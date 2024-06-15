package android.template.feature.weighbridge.ui.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun WeighedItemSortDialog(
    selectedSortType: SortType,
    onSelected: (type: SortType) -> Unit
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start
            ) {

                SortSelection(
                    label = "Newest Created",
                    isSelected = selectedSortType == SortType.Newest,
                    onClicked = { onSelected(SortType.Newest) }
                )
                SortSelection(
                    label = "Oldest Created",
                    isSelected = selectedSortType == SortType.Oldest,
                    onClicked = { onSelected(SortType.Oldest) }
                )
                SortSelection(
                    label = "Driver Name (Asc)",
                    isSelected = selectedSortType == SortType.DriverAsc,
                    onClicked = { onSelected(SortType.DriverAsc) }
                )
                SortSelection(
                    label = "Driver Name (Desc)",
                    isSelected = selectedSortType == SortType.DriverDesc,
                    onClicked = { onSelected(SortType.DriverDesc) }
                )
            }
        }
    }
}

@Composable
internal fun SortSelection(
    label: String,
    isSelected: Boolean,
    onClicked: () -> Unit
) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { onClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 20.dp),
            selected = isSelected,
            onClick = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = label, fontSize = 24.sp
        )
    }

}

sealed class SortType {
    object Newest: SortType()
    object Oldest: SortType()
    object DriverAsc: SortType()
    object DriverDesc: SortType()
}

@Preview
@Composable
private fun Preview() {
    WeighedItemSortDialog(SortType.Newest) {}
}
