package android.template.feature.weighbridge.ui.list

import android.template.core.ui.MyApplicationTheme
import android.template.feature.weighbridge.ui.WeighedItemUI
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeighedItem(weighedItem: WeighedItemUI, onClick: (id: Int) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = {
            onClick(weighedItem.id)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
//        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = weighedItem.driver,
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
//                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
//                    style = typography.subtitle1
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = weighedItem.dateTime,
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
//                    color = MaterialTheme.colors.surface,
//                    style = typography.caption
                )

            }

            LicenseTag(weighedItem.license)
        }
    }
}

@Composable
fun LicenseTag(license: String) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(Color.Green.copy(.08f))
    ) {
        Text(
            text = license, modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
            fontWeight = FontWeight.Bold,
//            style = MaterialTheme.typography.caption,
            color = Color.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeighedItemPreview() {
    MyApplicationTheme {
        WeighedItem(
            WeighedItemUI(
            id = 0,
            dateTime = "14 Jun 2024 13:35",
            license = "B 7772 DAB",
            driver = "Komang",
            inbound = "0,2",
            outbound = "0.45",
            netWeight = "0.25"
        )
        ) {}
    }
}
