package android.template.feature.weighbridge.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CreateWeighedItemScreen(navController: NavController) {

    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = "",
                onValueChange = { newText ->

                },
                label = {
                    Text(text = "License Number")
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { newText ->

                },
                label = {
                    Text(text = "Driver Name")
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { newText ->

                },
                label = {
                    Text(text = "Inbound")
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { newText ->

                },
                label = {
                    Text(text = "Outbound")
                }
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
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Net Weight", fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Net Weight", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CreateWeighedItemScreen(rememberNavController())
}
