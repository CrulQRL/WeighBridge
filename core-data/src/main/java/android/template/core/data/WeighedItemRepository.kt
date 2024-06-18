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

package android.template.core.data

import android.template.core.data.datamap.SortType
import android.template.core.data.datamap.WeighedItem
import android.template.core.data.datamap.mapModel
import android.template.core.data.datamap.toModel
import kotlinx.coroutines.flow.Flow
import android.template.core.database.WeighedItemModel
import android.template.core.database.WeighedItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WeighedItemRepository {
    suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String? = null
    ): Long

    fun getItemFlow(uid: Long): Flow<WeighedItem>

    suspend fun getItem(uid: Long): WeighedItem

    fun getItems(sortType: SortType, query: String): Flow<List<WeighedItem>>

    suspend fun update(item: WeighedItem): Int
}

class DefaultWeighedItemRepository @Inject constructor(
    private val weighedItemDao: WeighedItemDao
) : WeighedItemRepository {

    override suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String?
    ): Long {
        return weighedItemDao.insertWeighedItem(
            WeighedItemModel(
                dateTime = dateTime,
                license = license,
                driver = driver,
                inbound = inbound,
                outbound = outbound,
                netWeight = netWeight
            )
        )
    }


    override fun getItemFlow(uid: Long): Flow<WeighedItem> {
        return weighedItemDao.getWeighedItem(uid).map { it.mapModel() }
    }

    override suspend fun getItem(uid: Long): WeighedItem {
        return weighedItemDao.getWeighedItem(uid).first().mapModel()
    }

    override fun getItems(sortType: SortType, query: String): Flow<List<WeighedItem>> {
        return when (sortType) {
            SortType.Newest -> {
                weighedItemDao.getSortedWeighedItemsByDateDesc(query).map { models -> models.map { it.mapModel() } }
            }
            SortType.Oldest -> {
                weighedItemDao.getSortedWeighedItemsByDateAsc(query).map { models -> models.map { it.mapModel() } }
            }
            SortType.DriverAsc -> {
                weighedItemDao.getSortedWeighedItemsByDriverAsc(query).map { models -> models.map { it.mapModel() } }
            }
            SortType.DriverDesc -> {
                weighedItemDao.getSortedWeighedItemsByDriverDesc(query).map { models -> models.map { it.mapModel() } }
            }
        }
    }

    override suspend fun update(item: WeighedItem): Int {
        return weighedItemDao.updateWeighedItem(item.toModel())
    }
}
