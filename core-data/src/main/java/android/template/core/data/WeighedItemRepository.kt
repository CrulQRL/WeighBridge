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

import android.template.core.data.datamap.WeighedItem
import android.template.core.data.datamap.mapModel
import kotlinx.coroutines.flow.Flow
import android.template.core.database.WeighedItemModel
import android.template.core.database.WeighedItemDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WeighedItemRepository {
    val weighedItemModels: Flow<List<WeighedItem>>

    suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String
    )
}

class DefaultWeighedItemRepository @Inject constructor(
    private val weighedItemDao: WeighedItemDao
) : WeighedItemRepository {

    override val weighedItemModels: Flow<List<WeighedItem>> =
        weighedItemDao.getWeighedItems().map { models -> models.map { it.mapModel() } }

    override suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String
    ) {
        weighedItemDao.insertWeighedItem(
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
}
