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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.SortType
import android.template.feature.weighbridge.ui.list.uimodel.ListWeighedItemModel
import android.template.feature.weighbridge.ui.list.uimodel.mapUI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class WeighedItemViewModel @Inject constructor(
    weighedItemRepository: WeighedItemRepository
) : ViewModel() {

    private val selectedSortType = MutableStateFlow<SortType>(SortType.Newest)
    private val query = MutableStateFlow("")


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: Flow<List<ListWeighedItemModel>> = combine(selectedSortType, query) { sort, query ->
            Pair(sort, query)
    }
        .debounce(timeoutMillis = 400)
        .distinctUntilChanged()
        .flatMapLatest {
            weighedItemRepository.getItems(it.first, it.second)
        }
        .map { items ->
            items.mapUI()
        }

    fun updateSortType(sortType: SortType) {
        selectedSortType.value = sortType
    }

    fun updateQuery(newQuery: String) {
        query.value = newQuery
    }

}
