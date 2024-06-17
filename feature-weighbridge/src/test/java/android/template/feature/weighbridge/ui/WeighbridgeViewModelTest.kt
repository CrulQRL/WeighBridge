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

package android.template.feature.weighbridge.ui


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.SortType
import android.template.core.data.datamap.WeighedItem
import android.template.feature.weighbridge.ui.list.WeighedItemViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
        assertEquals(item.size, 2)
        assertEquals(item[0].uid, 7)
        assertEquals(item[1].uid, 6)
    }

    @Test
    fun uiState_onSortTypeUpdated() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        viewModel.updateSortType(SortType.Oldest)
        val item = viewModel.uiState.first()
        assertEquals(item.size, 2)
        assertEquals(item[0].uid, 6)
        assertEquals(item[1].uid, 7)
    }

    @Test
    fun uiState_onQueryDriverUpdated() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        viewModel.updateQuery("Ko")
        val item = viewModel.uiState.first()
        assertEquals(item.size, 1)
        assertEquals(item[0].uid, 6)
    }

    @Test
    fun uiState_onQueryLicenseUpdated() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        viewModel.updateQuery("211")
        val item = viewModel.uiState.first()
        assertEquals(item.size, 1)
        assertEquals(item[0].uid, 7)
    }
}

private class FakeWeighedItemRepository : WeighedItemRepository {

    private val data = mutableListOf(
        WeighedItem(
            uid = 6,
            dateTime = 1718546330712, // 16 June 2024 08:58
            license = "B 6572 CCA",
            driver = "Komang",
            inbound = "0.3",
            outbound = "0.7",
            netWeight = "0.4"
        ),
        WeighedItem(
            uid = 7,
            dateTime = 1718630387798, // 17 June 2024 08:19
            license = "B 1211 OOP",
            driver = "Budi",
            inbound = "0.2",
            outbound = "0.7",
            netWeight = "0.5"
        )
    )

    override val weighedItemModels: Flow<List<WeighedItem>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String?
    ): Long {
        return 0
//        data.add(0, WeighedItem())
    }

    override fun getItemFlow(uid: Long): Flow<WeighedItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getItem(uid: Long): WeighedItem {
        TODO("Not yet implemented")
    }

    override fun getItems(sortType: SortType, query: String): Flow<List<WeighedItem>> {
        val queried = data.filter { it.driver.contains(query) || it.license.contains(query) }
        return when(sortType) {
            SortType.Newest -> {
                flowOf(queried.sortedByDescending { it.dateTime })
            }
            SortType.Oldest -> {
                flowOf(queried.sortedBy { it.dateTime })
            }
            SortType.DriverDesc -> {
                flowOf(queried.sortedByDescending { it.driver })
            }
            SortType.DriverAsc -> {
                flowOf(queried.sortedBy { it.driver })
            }
        }
    }

    override suspend fun update(item: WeighedItem): Int {
        TODO("Not yet implemented")
    }
}
