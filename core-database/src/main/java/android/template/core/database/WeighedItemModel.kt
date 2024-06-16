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

package android.template.core.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "weighed_item_model")
data class WeighedItemModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    val uid: Long = 0,
    @ColumnInfo(name = "date_time")
    val dateTime: Long,
    @ColumnInfo(name = "license")
    val license: String,
    @ColumnInfo(name = "driver")
    val driver: String,
    @ColumnInfo(name = "inbound")
    val inbound: String,
    @ColumnInfo(name = "outbound")
    val outbound: String,
    @ColumnInfo(name = "net_weight")
    val netWeight: String? = null
)

@Dao
interface WeighedItemDao {
    @Query("SELECT * FROM weighed_item_model ORDER BY uid DESC LIMIT 10")
    fun getWeighedItems(): Flow<List<WeighedItemModel>>

    @Insert
    suspend fun insertWeighedItem(item: WeighedItemModel): Long

    @Query("SELECT * FROM weighed_item_model where uid = :uid")
    fun getWeighedItem(uid: Long): Flow<WeighedItemModel>
}
