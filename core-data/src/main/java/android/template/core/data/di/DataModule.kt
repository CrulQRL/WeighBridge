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

package android.template.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import android.template.core.data.WeighedItemRepository
import android.template.core.data.DefaultWeighedItemRepository
import android.template.core.data.datamap.SortType
import android.template.core.data.datamap.WeighedItem
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsWeighedItemRepository(
        myModelRepository: DefaultWeighedItemRepository
    ): WeighedItemRepository
}

class FakeWeighedItemRepository @Inject constructor() : WeighedItemRepository {

    private val data = fakeMyModels

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
        return flowOf(data.first { it.uid == uid })
    }

    override suspend fun getItem(uid: Long): WeighedItem {
        return data.first { item -> item.uid == uid }
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

val fakeMyModels = mutableListOf(
    WeighedItem(
        uid = 6,
        dateTime = 1718546330712, // 16 June 2024 08:58
        license = "B6572CCA",
        driver = "Komang",
        inbound = "0.3",
        outbound = "0.7",
        netWeight = "0.4"
    ),
    WeighedItem(
        uid = 7,
        dateTime = 1718630387798, // 17 June 2024 08:19
        license = "B1211OOP",
        driver = "Budi",
        inbound = "0.2",
        outbound = "0.7",
        netWeight = "0.5"
    )
)
