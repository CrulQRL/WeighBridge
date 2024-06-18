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
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import android.template.core.data.DefaultWeighedItemRepository
import android.template.core.data.datamap.WeighedItem
import android.template.core.database.WeighedItemModel
import android.template.core.database.WeighedItemDao
import kotlinx.coroutines.flow.flowOf

/**
 * Unit tests for [DefaultWeighedItemRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultWeighedItemRepositoryTest {

    @Test
    fun weighedItem_newItemSaved() = runTest {
        val repository = DefaultWeighedItemRepository(FakeWeighedItemDao())

        val uid = repository.add(
            dateTime = 1718546330712, // 16 June 2024 08:58
            license = "B6572CCA",
            driver = "Komang",
            inbound = "0.3",
            outbound = "0.7",
            netWeight = "0.4"
        )

        assertEquals(uid, 10L)
        assertEquals(repository.getItem(10L).license, "B6572CCA")
        assertEquals(repository.getItem(10L).driver, "Komang")
        assertEquals(repository.getItem(10L).inbound, "0.3")
        assertEquals(repository.getItem(10L).outbound, "0.7")
        assertEquals(repository.getItem(10L).netWeight, "0.4")
    }

    @Test
    fun weighedItem_geDetailFlow() = runTest {
        val repository = DefaultWeighedItemRepository(FakeWeighedItemDao())

        val uid = repository.add(
            dateTime = 1718630387798, // 17 June 2024 08:19
            license = "B1211OOP",
            driver = "Budi",
            inbound = "0.2",
            outbound = "0.7",
            netWeight = "0.5"
        )

        assertEquals(repository.getItemFlow(uid).first().license, "B1211OOP")
        assertEquals(repository.getItemFlow(uid).first().driver, "Budi")
        assertEquals(repository.getItemFlow(uid).first().inbound, "0.2")
        assertEquals(repository.getItemFlow(uid).first().outbound, "0.7")
        assertEquals(repository.getItemFlow(uid).first().netWeight, "0.5")
    }

    @Test
    fun weighedItem_updateItem() = runTest {
        val repository = DefaultWeighedItemRepository(FakeWeighedItemDao())

        val uid = repository.add(
            dateTime = 1718546330712, // 16 June 2024 08:58
            license = "B6572CCA",
            driver = "Komang",
            inbound = "0.3",
            outbound = "0.7",
            netWeight = "0.4"
        )

        val newItem = WeighedItem(
            uid = uid,
            dateTime = 1718630387798, // 17 June 2024 08:19
            license = "B1211OOP",
            driver = "Budi",
            inbound = "0.2",
            outbound = "0.7",
            netWeight = "0.5"
        )

        repository.update(newItem)

        assertEquals(repository.getItem(uid).license, "B1211OOP")
        assertEquals(repository.getItem(uid).driver, "Budi")
        assertEquals(repository.getItem(uid).inbound, "0.2")
        assertEquals(repository.getItem(uid).outbound, "0.7")
        assertEquals(repository.getItem(uid).netWeight, "0.5")
    }

}

private class FakeWeighedItemDao : WeighedItemDao {

    private val data = mutableListOf<WeighedItemModel>()

    override fun getWeighedItems(): Flow<List<WeighedItemModel>> {
        return flowOf(data)
    }

    override suspend fun insertWeighedItem(item: WeighedItemModel): Long {
        val newLocalId = 10L
        data.add(item.copy(uid = newLocalId))
        return newLocalId
    }

    override fun getWeighedItem(uid: Long): Flow<WeighedItemModel> {
        return flowOf(data.first { data -> data.uid == uid })
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
        val index = data.indexOfFirst { data -> data.uid == item.uid }
        data[index] = item
        return 1
    }
}
