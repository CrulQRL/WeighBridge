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

package android.template.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import android.template.core.data.DefaultWeighedItemRepository
import android.template.core.database.WeighedItemModel
import android.template.core.database.WeighedItemDao

/**
 * Unit tests for [DefaultWeighedItemRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultWeighedItemRepositoryTest {

    @Test
    fun myModels_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultWeighedItemRepository(FakeWeighedItemDao())

//        repository.add("Repository")

        assertEquals(repository.weighedItemModels.first().size, 1)
    }

}

private class FakeWeighedItemDao : WeighedItemDao {

    private val data = mutableListOf<WeighedItemModel>()

    override fun getWeighedItems(): Flow<List<WeighedItemModel>> = flow {
        emit(data)
    }

    override suspend fun insertWeighedItem(item: WeighedItemModel): Long {
        TODO("Not yet implemented")
    }

    override fun getWeighedItem(uid: Long): Flow<WeighedItemModel> {
        TODO("Not yet implemented")
    }

    override fun getSortedWeighedItemsByDateDesc(query: String): Flow<List<WeighedItemModel>> {
        TODO("Not yet implemented")
    }

    override fun getSortedWeighedItemsByDateAsc(query: String): Flow<List<WeighedItemModel>> {
        TODO("Not yet implemented")
    }

    override fun getSortedWeighedItemsByDriverAsc(query: String): Flow<List<WeighedItemModel>> {
        TODO("Not yet implemented")
    }

    override fun getSortedWeighedItemsByDriverDesc(query: String): Flow<List<WeighedItemModel>> {
        TODO("Not yet implemented")
    }

    override fun updateWeighedItem(item: WeighedItemModel): Int {
        TODO("Not yet implemented")
    }
}
