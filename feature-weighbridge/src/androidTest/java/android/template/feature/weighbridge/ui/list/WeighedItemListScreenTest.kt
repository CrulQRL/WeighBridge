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

import android.template.feature.weighbridge.ui.list.WeighedItemListScreen
import android.template.feature.weighbridge.ui.list.uimodel.ListWeighedItemModel
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for [WeighedItemListScreen].
 */
@RunWith(AndroidJUnit4::class)
class WeighedItemListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            WeighedItemListScreen(
                rememberNavController(),
                FAKE_DATA,
                {},
                {}
            )
        }
    }
    @Test
    fun firstItem_exists() {
        composeTestRule.onNodeWithText("Komang").assertExists()
        composeTestRule.onNodeWithText("B1318OPE").assertExists()
    }
}

private val FAKE_DATA = listOf(
    ListWeighedItemModel(
        uid = 0,
        dateTime = "14 Jun 2024 13:35",
        license = "B6572CCA",
        driver = "Komang"
    ),
    ListWeighedItemModel(
        uid = 1,
        dateTime = "15 Jun 2024 13:35",
        license = "B7772DAB",
        driver = "Budi"
    ),
    ListWeighedItemModel(
        uid = 2,
        dateTime = "16 Jun 2024 13:35",
        license = "B1318OPE",
        driver = "Sudarso"
    )
)
