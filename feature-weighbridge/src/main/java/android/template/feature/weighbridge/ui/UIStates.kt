package android.template.feature.weighbridge.ui

data class WeighedItemUI(
    val id: Int,
    val dateTime: String,
    val license: String,
    val driver: String,
    val inbound: String,
    val outbound: String,
    val netWeight: String
)
