package android.template.feature.weighbridge.ui.detail

import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.WeighedItem
import android.template.feature.weighbridge.ui.detail.uimodel.DetailWeighedItemModel
import android.template.feature.weighbridge.ui.detail.uimodel.mapUI
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailWeighedItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    weighedItemRepository: WeighedItemRepository
): ViewModel() {

    val uiState: StateFlow<DetailWeighedItemUiState> = weighedItemRepository
        .getItem(savedStateHandle["uid"]!!)
        .map<WeighedItem, DetailWeighedItemUiState> { item -> DetailWeighedItemUiState.Success(item.mapUI()) }
        .catch { emit(DetailWeighedItemUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailWeighedItemUiState.Loading)

}

sealed interface DetailWeighedItemUiState {
    object Loading : DetailWeighedItemUiState
    data class Error(val throwable: Throwable) : DetailWeighedItemUiState
    data class Success(val data: DetailWeighedItemModel) : DetailWeighedItemUiState
}
