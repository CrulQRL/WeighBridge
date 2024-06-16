package android.template.feature.weighbridge.ui.list

import android.template.core.data.datamap.WeighedItem
import java.text.SimpleDateFormat
import java.util.Locale

data class ListUIState(
    val uid: Int,
    val dateTime: String,
    val driver: String,
    val license: String
)
fun List<WeighedItem>.mapUI(): List<ListUIState> {
    val formatter = SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.getDefault())

    return this.map { item ->
        ListUIState(
            uid = item.uid,
            dateTime = formatter.format(item.dateTime),
            driver = item.driver,
            license = item.license
        )
    }
}
