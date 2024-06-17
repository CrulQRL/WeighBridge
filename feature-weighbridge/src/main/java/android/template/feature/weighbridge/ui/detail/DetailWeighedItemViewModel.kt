package android.template.feature.weighbridge.ui.detail

import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.WeighedItem
import android.template.feature.weighbridge.ui.detail.uimodel.DetailWeighedItemModel
import android.template.feature.weighbridge.ui.detail.uimodel.mapUI
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DetailWeighedItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    weighedItemRepository: WeighedItemRepository
): ViewModel() {

    val uiState: Flow<DetailWeighedItemUiState> = weighedItemRepository
        .getItemFlow(savedStateHandle["uid"]!!)
        .map<WeighedItem, DetailWeighedItemUiState> { item ->
            DetailWeighedItemUiState.Success(item.mapUI())
        }

}

sealed interface DetailWeighedItemUiState {
    object Loading : DetailWeighedItemUiState
    data class Error(val throwable: Throwable) : DetailWeighedItemUiState
    data class Success(val data: DetailWeighedItemModel) : DetailWeighedItemUiState
}
