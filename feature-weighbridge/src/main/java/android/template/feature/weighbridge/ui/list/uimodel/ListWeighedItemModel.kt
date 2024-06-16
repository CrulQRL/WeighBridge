package android.template.feature.weighbridge.ui.list.uimodel

import android.template.core.data.datamap.WeighedItem
import java.text.SimpleDateFormat
import java.util.Locale

data class ListWeighedItemModel(
    val uid: Int,
    val dateTime: String,
    val driver: String,
    val license: String
)
fun List<WeighedItem>.mapUI(): List<ListWeighedItemModel> {
    val formatter = SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.getDefault())

    return this.map { item ->
        ListWeighedItemModel(
            uid = item.uid,
            dateTime = formatter.format(item.dateTime),
            driver = item.driver,
            license = item.license
        )
    }
}
