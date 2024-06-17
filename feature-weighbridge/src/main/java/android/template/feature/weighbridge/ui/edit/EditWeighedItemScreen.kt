package android.template.feature.weighbridge.ui.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWeighedItemScreen(
    navController: NavController,
    viewModel: EditWeighedItemViewModel = hiltViewModel()
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState(
        initialValue = EditWeighedItemUiState(),
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    LaunchedEffect(key1 = uiState.isItemUpdated) {
        if (uiState.isItemUpdated) {
            navController.navigateUp()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Edit Item")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .semantics { contentDescription = "DriverName" }
                ,
                value = uiState.newDriver,
                onValueChange = { newText ->
                    viewModel.setDriver(newText)
                },
                label = {
                    Text(text = "Driver Name")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .semantics { contentDescription = "LicenseNumber" }
                ,
                value = uiState.newLicense,
                onValueChange = { newText ->
                    viewModel.setLicense(newText)
                },
                label = {
                    Text(text = "License Number")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                value = uiState.newInbound,
                onValueChange = { newText ->
                    viewModel.setInbound(newText)
                },
                isError = !uiState.isValidInbound,
                label = {
                    Text(text = "Inbound")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                value = uiState.newOutbound,
                onValueChange = { newText ->
                    viewModel.setOutbound(newText)
                },
                isError = !uiState.isValidOutbound,
                label = {
                    Text(text = "Outbound")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

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

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth())
                {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Net Weight", fontSize = 22.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = uiState.netWeight ?: "-", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = uiState.isValidForm,
                        onClick = { viewModel.updateWeighedItem() }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "Save", fontSize = 24.sp
                        )
                    }

                }
            }

        }
    }
}

@Preview
@Composable
private fun Preview() {
    EditWeighedItemScreen(rememberNavController())
}
