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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.WeighedItem
import android.template.feature.weighbridge.ui.list.WeighedItemUiState
import android.template.feature.weighbridge.ui.list.WeighedItemViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class WeighedItemViewModelTest {
    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        assertEquals(viewModel.uiState.first(), WeighedItemUiState.Loading)
    }

    @Test
    fun uiState_onItemSaved_isDisplayed() = runTest {
        val viewModel = WeighedItemViewModel(FakeWeighedItemRepository())
        assertEquals(viewModel.uiState.first(), WeighedItemUiState.Loading)
    }
}

private class FakeWeighedItemRepository : WeighedItemRepository {

    private val data = mutableListOf<WeighedItem>()

    override val weighedItemModels: Flow<List<WeighedItem>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String
    ) {
//        data.add(0, WeighedItem())
    }
}
