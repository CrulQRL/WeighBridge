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
    override val weighedItemModels: Flow<List<WeighedItem>> = flowOf(fakeMyModels)

    override suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String?
    ): Long {
        throw NotImplementedError()
    }

    override fun getItemFlow(uid: Long): Flow<WeighedItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getItem(uid: Long): WeighedItem {
        TODO("Not yet implemented")
    }

    override fun getItems(sortType: SortType, query: String): Flow<List<WeighedItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: WeighedItem): Int {
        TODO("Not yet implemented")
    }
}

val fakeMyModels = listOf<WeighedItem>()
