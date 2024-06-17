package android.template.core.data.datamap

import android.template.core.database.WeighedItemModel

data class WeighedItem(
    val uid: Long,
    val dateTime: Long,
    val license: String,
    val driver: String,
    val inbound: String,
    val outbound: String,
    val netWeight: String?
)

fun WeighedItemModel.mapModel(): WeighedItem {
    return WeighedItem(
        uid = this.uid,
        dateTime = this.dateTime,
        license = this.license,
        driver = this.driver,
        inbound = this.inbound,
        outbound = this.outbound,
        netWeight = this.netWeight
    )
}

fun WeighedItem.toModel(): WeighedItemModel {
    return WeighedItemModel(
        uid = this.uid,
        dateTime = this.dateTime,
        license = this.license,
        driver = this.driver,
        inbound = this.inbound,
        outbound = this.outbound,
        netWeight = this.netWeight
    )
}
