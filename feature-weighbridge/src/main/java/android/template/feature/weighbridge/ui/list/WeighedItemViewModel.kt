/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.template.feature.weighbridge.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.WeighedItem
import android.template.feature.weighbridge.ui.list.WeighedItemUiState.Error
import android.template.feature.weighbridge.ui.list.WeighedItemUiState.Loading
import android.template.feature.weighbridge.ui.list.WeighedItemUiState.Success
import android.template.feature.weighbridge.ui.list.uimodel.ListWeighedItemModel
import android.template.feature.weighbridge.ui.list.uimodel.mapUI
import javax.inject.Inject

@HiltViewModel
class WeighedItemViewModel @Inject constructor(
    weighedItemRepository: WeighedItemRepository
) : ViewModel() {

    val uiState: StateFlow<WeighedItemUiState> = weighedItemRepository
        .weighedItemModels.map<List<WeighedItem>, WeighedItemUiState> { Success(data = it.mapUI()) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

}

sealed interface WeighedItemUiState {
    object Loading : WeighedItemUiState
    data class Error(val throwable: Throwable) : WeighedItemUiState
    data class Success(val data: List<ListWeighedItemModel>) : WeighedItemUiState
}
