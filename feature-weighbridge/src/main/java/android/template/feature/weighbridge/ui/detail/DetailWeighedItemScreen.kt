package android.template.feature.weighbridge.ui.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@ExperimentalMaterial3Api
@Composable
fun DetailWeighedItemScreen(
    navController: NavController,
    viewModel: DetailWeighedItemViewModel = hiltViewModel()
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val item by produceState<DetailWeighedItemUiState>(
        initialValue = DetailWeighedItemUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Detail Item")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (item is DetailWeighedItemUiState.Success) {
            Column(modifier = Modifier.padding(paddingValues)) {

                Log.d("Lol", "Masuk Detail")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate("edit/${(item as DetailWeighedItemUiState.Success).data.uid}")
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Edit Item", fontSize = 16.sp)
                            Spacer(
                                modifier = Modifier
                                    .width(8.dp)
                            )
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Edit icon"
                            )
                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = "Driver Name",
                    fontSize = 16.sp
                )
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = (item as DetailWeighedItemUiState.Success).data.driver,
                        fontSize = 20.sp
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = "License Number",
                    fontSize = 16.sp
                )
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = (item as DetailWeighedItemUiState.Success).data.license,
                        fontSize = 20.sp
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = "Date",
                    fontSize = 16.sp
                )
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = (item as DetailWeighedItemUiState.Success).data.dateTime,
                        fontSize = 20.sp
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = "Inbound Weight",
                    fontSize = 16.sp
                )
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = (item as DetailWeighedItemUiState.Success).data.inbound,
                        fontSize = 20.sp
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    text = "Outbound Weight",
                    fontSize = 16.sp
                )
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = (item as DetailWeighedItemUiState.Success).data.outbound,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Card(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
//              backgroundColor = MaterialTheme.colors.onSurface
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Net Weight", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = (item as DetailWeighedItemUiState.Success).data.netWeight, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }


    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
private fun Preview() {
    DetailWeighedItemScreen(rememberNavController())
}
