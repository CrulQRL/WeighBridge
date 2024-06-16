package android.template.feature.weighbridge.ui.detail.uimodel

import android.template.core.data.datamap.WeighedItem
import java.text.SimpleDateFormat
import java.util.Locale

data class DetailWeighedItemModel(
    val uid: Long = 0,
    val dateTime: String = "",
    val license: String = "",
    val driver: String = "",
    val inbound: String = "",
    val outbound: String = "",
    val netWeight: String = ""
)

fun WeighedItem.mapUI(): DetailWeighedItemModel {
    val formatter = SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.getDefault())

    return DetailWeighedItemModel(
        uid = this.uid,
        dateTime = formatter.format(this.dateTime),
        driver = this.driver,
        license = this.license,
        inbound = this.inbound,
        outbound = this.outbound,
        netWeight = this.netWeight ?: "-"
    )
}
