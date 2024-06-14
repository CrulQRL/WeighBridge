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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import android.template.core.ui.MyApplicationTheme
import android.template.feature.weighbridge.ui.WeighedItemUI
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WeighedItemListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MyModelViewModel = hiltViewModel()
)
{
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<MyModelUiState>(
        initialValue = MyModelUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    val listWeighedData = listOf(
        WeighedItemUI(
            id = 0,
            dateTime = "14 Jun 2024 13:35",
            license = "B 6572 CCA",
            driver = "Komang",
            inbound = "0,2",
            outbound = "0.45",
            netWeight = "0.25"
        ),
        WeighedItemUI(
            id = 1,
            dateTime = "15 Jun 2024 13:35",
            license = "B 7772 DAB",
            driver = "Budi",
            inbound = "0,2",
            outbound = "0.45",
            netWeight = "0.25"
        ),
        WeighedItemUI(
            id = 2,
            dateTime = "16 Jun 2024 13:35",
            license = "B 1318 OPE",
            driver = "Sudarso",
            inbound = "0,2",
            outbound = "0.45",
            netWeight = "0.25"
        )
    )

    WeighedItemListScreen(navController, listWeighedData)

//    if (items is Success) {
//        WeighedItemListScreen(
//            items = (items as Success).data,
//            onSave = { name -> viewModel.addMyModel(name) },
//            modifier = modifier
//        )
//    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeighedItemListScreen(
    navController: NavController,
    listWeighedData: List<WeighedItemUI>? = null
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("List Item")
                }
            )
        },
        floatingActionButton = {
            CreateWeighedItemButton {
                navController.navigate("create")
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            var nameMyModel by remember { mutableStateOf("Compose") }

            listWeighedData?.forEach { data ->
                WeighedItem(weighedItem = data) {
                    navController.navigate("detail")
                }
            }

        }
    }
}

@Composable
internal fun CreateWeighedItemButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .padding(0.dp, 0.dp, 24.dp, 24.dp)
            .height(86.dp)
            .width(86.dp)
        ,
        shape = CircleShape,
        onClick = { onClick() },
    ) {
        Icon(
            modifier = Modifier
                .height(46.dp)
                .width(46.dp)
            ,
            imageVector = Icons.Filled.Add,
            contentDescription = "Create WeighedItem button"
        )
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        WeighedItemListScreen(
            navController = rememberNavController(),
            listWeighedData = listOf(
                WeighedItemUI(
                    id = 0,
                    dateTime = "14 Jun 2024 13:35",
                    license = "B 6572 CCA",
                    driver = "Komang",
                    inbound = "0,2",
                    outbound = "0.45",
                    netWeight = "0.25"
                ),
                WeighedItemUI(
                    id = 1,
                    dateTime = "15 Jun 2024 13:35",
                    license = "B 7772 DAB",
                    driver = "Budi",
                    inbound = "0,2",
                    outbound = "0.45",
                    netWeight = "0.25"
                ),
                WeighedItemUI(
                    id = 2,
                    dateTime = "16 Jun 2024 13:35",
                    license = "B 1318 OPE",
                    driver = "Sudarso",
                    inbound = "0,2",
                    outbound = "0.45",
                    netWeight = "0.25"
                )
            )
        )
    }
}

//@Preview(showBackground = true, widthDp = 480)
//@Composable
//private fun PortraitPreview() {
//    MyApplicationTheme {
//        MyModelScreen(onSave = {})
//    }
//}
