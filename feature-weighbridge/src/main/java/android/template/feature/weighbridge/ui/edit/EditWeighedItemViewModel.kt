package android.template.feature.weighbridge.ui.edit

import android.template.core.data.WeighedItemRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditWeighedItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: WeighedItemRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(EditWeighedItemUiState())
    val uiState: StateFlow<EditWeighedItemUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                val item = repository.getItem(savedStateHandle["uid"]!!)
                currentState.copy(
                    newLicense = item.license, newDriver = item.driver,
                    newInbound = item.inbound, newOutbound = item.outbound
                )
            }
        }
    }

}

data class EditWeighedItemUiState(
    val newLicense: String = "",
    val newDriver: String = "",
    val newInbound: String = "",
    val newOutbound: String = "",
    val isValidInbound: Boolean = true,
    val isValidOutbound: Boolean = true,
    val isValidForm: Boolean = true
)
