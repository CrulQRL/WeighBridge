package android.template.core.data.datamap

sealed class SortType {
    object Newest: SortType()
    object Oldest: SortType()
    object DriverAsc: SortType()
    object DriverDesc: SortType()
}
