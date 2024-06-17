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


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import android.template.core.data.datamap.SortType
import android.template.feature.weighbridge.FakeWeighedItemRepository
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class WeighedItemViewModelTest {
    @Test
    fun uiState_initialListLoaded() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        val item = viewModel.uiState.first()
        assertEquals(2, item.size)
        assertEquals(7, item[0].uid)
        assertEquals(6, item[1].uid)
    }

    @Test
    fun uiState_onSortTypeUpdated() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        viewModel.updateSortType(SortType.Oldest)
        val item = viewModel.uiState.first()
        assertEquals(2, item.size)
        assertEquals(6, item[0].uid)
        assertEquals(7, item[1].uid)
    }

    @Test
    fun uiState_onQueryDriverUpdated() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        viewModel.updateQuery("Ko")
        val item = viewModel.uiState.first()
        assertEquals(1, item.size)
        assertEquals(6, item[0].uid)
    }

    @Test
    fun uiState_onQueryLicenseUpdated() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        viewModel.updateQuery("211")
        val item = viewModel.uiState.first()
        assertEquals(1, item.size)
        assertEquals(7, item[0].uid)
    }
}
