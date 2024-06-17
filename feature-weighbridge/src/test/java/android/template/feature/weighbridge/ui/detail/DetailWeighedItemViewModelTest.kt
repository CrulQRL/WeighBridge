package android.template.feature.weighbridge.ui.detail

import android.template.feature.weighbridge.FakeWeighedItemRepository
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DetailWeighedItemViewModelTest {

    @Test
    fun uiState_getUid_6() = runTest {
        val viewModel = DetailWeighedItemViewModel(
            SavedStateHandle().apply {
                set("uid", 6L)
            },
            FakeWeighedItemRepository()
        )

        val item = viewModel.uiState.first() as DetailWeighedItemUiState.Success
        Assert.assertEquals(item.data.uid, 6)
        Assert.assertEquals(item.data.driver, "Komang")
    }

}
