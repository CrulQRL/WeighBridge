package android.template.feature.weighbridge

import android.template.core.data.WeighedItemRepository
import android.template.core.data.datamap.SortType
import android.template.core.data.datamap.WeighedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeWeighedItemRepository : WeighedItemRepository {

    private val data = mutableListOf(
        WeighedItem(
            uid = 6,
            dateTime = 1718546330712, // 16 June 2024 08:58
            license = "B 6572 CCA",
            driver = "Komang",
            inbound = "0.3",
            outbound = "0.7",
            netWeight = "0.4"
        ),
        WeighedItem(
            uid = 7,
            dateTime = 1718630387798, // 17 June 2024 08:19
            license = "B 1211 OOP",
            driver = "Budi",
            inbound = "0.2",
            outbound = "0.7",
            netWeight = "0.5"
        )
    )

    override suspend fun add(
        dateTime: Long,
        license: String,
        driver: String,
        inbound: String,
        outbound: String,
        netWeight: String?
    ): Long {
        return 0
//        data.add(0, WeighedItem())
    }

    override fun getItemFlow(uid: Long): Flow<WeighedItem> {
        return flowOf(data.first { it.uid == uid })
    }

    override suspend fun getItem(uid: Long): WeighedItem {
        TODO("Not yet implemented")
    }

    override fun getItems(sortType: SortType, query: String): Flow<List<WeighedItem>> {
        val queried = data.filter { it.driver.contains(query) || it.license.contains(query) }
        return when(sortType) {
            SortType.Newest -> {
                flowOf(queried.sortedByDescending { it.dateTime })
            }
            SortType.Oldest -> {
                flowOf(queried.sortedBy { it.dateTime })
            }
            SortType.DriverDesc -> {
                flowOf(queried.sortedByDescending { it.driver })
            }
            SortType.DriverAsc -> {
                flowOf(queried.sortedBy { it.driver })
            }
        }
    }

    override suspend fun update(item: WeighedItem): Int {
        TODO("Not yet implemented")
    }
}
