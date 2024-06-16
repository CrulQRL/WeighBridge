package android.template.feature.weighbridge.ui.create

import android.template.core.data.WeighedItemRepository
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateWeighedItemViewModel @Inject constructor(
    weighedItemRepository: WeighedItemRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CreateWeighedItemUiState())
    val uiState: StateFlow<CreateWeighedItemUiState> = _uiState.asStateFlow()

    fun setLicense(newLicense: String) {
        val formattedLicense = newLicense.filter { char -> char.isDigit() || char.isLetter()}
        _uiState.update { currentState ->
            currentState.copy(newLicense = formattedLicense)
        }

        updateIsValidForm()
    }

    fun setDriver(newDriver: String) {
        val formattedDriver = newDriver.filter { char -> char.isLetter() || char.isWhitespace() }
        _uiState.update { currentState ->
            currentState.copy(newDriver = formattedDriver)
        }

        updateIsValidForm()
    }

    fun setInbound(newInbound: String) {

        try {
            newInbound.replace(",", ".").toDouble()
            _uiState.update { currentState ->
                currentState.copy(newInbound = newInbound, isValidInbound = true)
            }
            updateIsValidForm()
        } catch (ex: NumberFormatException) {
            _uiState.update { currentState ->
                currentState.copy(newInbound = newInbound, isValidInbound = false, isValidForm = false)
            }
        }
    }

    fun setOutbound(newOutbound: String) {

        try {
            newOutbound.replace(",", ".").toDouble()
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
            val isValid = currentState.newLicense.isNotBlank()
                    && currentState.newDriver.isNotBlank()
                    && currentState.newInbound.isNotBlank()
                    && currentState.newOutbound.isNotBlank()
                    && currentState.isValidInbound
                    && currentState.isValidOutbound

            currentState.copy(isValidForm = isValid)
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
    val isLoading: Boolean = false
)
