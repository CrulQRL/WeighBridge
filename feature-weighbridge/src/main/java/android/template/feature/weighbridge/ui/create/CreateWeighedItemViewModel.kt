package android.template.feature.weighbridge.ui.create

import android.template.core.data.WeighedItemRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CreateWeighedItemViewModel @Inject constructor(
    private val weighedItemRepository: WeighedItemRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateWeighedItemUiState())
    val uiState: StateFlow<CreateWeighedItemUiState> = _uiState.asStateFlow()

    fun setDriver(newDriver: String) {
        val formattedDriver = newDriver.filter { char -> char.isLetter() || char.isWhitespace() }.replace("\\s+".toRegex(), " ")

        _uiState.update { currentState ->
            currentState.copy(newDriver = formattedDriver)
        }

        updateIsValidForm()
    }

    fun setLicense(newLicense: String) {
        if (newLicense.length > 8) return

        val formattedLicense = newLicense.filter { char -> char.isDigit() || char.isLetter()}
        _uiState.update { currentState ->
            currentState.copy(newLicense = formattedLicense)
        }

        updateIsValidForm()
    }

    fun setInbound(newInbound: String) {

        if (newInbound.length > 5) return

        try {
            val inboundNum = newInbound.replace(",", ".").toDouble()
            _uiState.update { currentState ->
                currentState.copy(newInbound = newInbound, isValidInbound = inboundNum != 0.0)
            }
            updateIsValidForm()
        } catch (ex: NumberFormatException) {
            _uiState.update { currentState ->
                currentState.copy(newInbound = newInbound, isValidInbound = false, isValidForm = false)
            }
        }
    }

    fun setOutbound(newOutbound: String) {

        if (newOutbound.length > 5) return

        try {
            if (newOutbound.isNotBlank()) {
                newOutbound.replace(",", ".").toDouble()
            }
            _uiState.update { currentState ->
                currentState.copy(newOutbound = newOutbound, isValidOutbound = true)
            }
            updateIsValidForm()
        } catch (ex: NumberFormatException) {
            _uiState.update { currentState ->
                currentState.copy(newOutbound = newOutbound, isValidOutbound = false, isValidForm = false)
            }
        }
    }

    private fun updateIsValidForm() {
        _uiState.update { currentState ->
            val isValidInput = currentState.newLicense.isNotBlank()
                    && currentState.newDriver.isNotBlank()
                    && currentState.newInbound.isNotBlank()
                    && currentState.isValidInbound
                    && currentState.isValidOutbound

            val newOutbound = if (uiState.value.newOutbound.isNotBlank())
                uiState.value.newOutbound.replace(",", ".")
            else "0"
            val newInbound = uiState.value.newInbound.replace(",", ".")

            val isValidNetWeight = if (newOutbound != "0") {
                newOutbound.toDouble() - newInbound.toDouble() > 0.0
            } else true

            currentState.copy(isValidForm = isValidInput && isValidNetWeight, isValidOutbound = isValidNetWeight)
        }
    }

    fun submitNewWeighedItem() {

        val newOutbound = if (uiState.value.newOutbound.isNotBlank())
            uiState.value.newOutbound.replace(",", ".")
        else "0"
        val newInbound = uiState.value.newInbound.replace(",", ".")

        val netWeight = if (newOutbound != "0")  {
            val diff = newOutbound.toDouble() - newInbound.toDouble()
            BigDecimal(diff).setScale(3, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString()
        } else null

        viewModelScope.launch(Dispatchers.IO) {
            val id = weighedItemRepository.add(
                dateTime = System.currentTimeMillis(),
                license = uiState.value.newLicense,
                driver = uiState.value.newDriver.trim(),
                inbound = newInbound,
                outbound = newOutbound,
                netWeight = netWeight
            )

            _uiState.update { currentState -> currentState.copy(newWeighedItemId = id) }
        }
    }
}

data class CreateWeighedItemUiState(
    val newLicense: String = "",
    val newDriver: String = "",
    val newInbound: String = "",
    val newOutbound: String = "",
    val isValidInbound: Boolean = true,
    val isValidOutbound: Boolean = true,
    val isValidForm: Boolean = false,
    val newWeighedItemId: Long? = null
)
