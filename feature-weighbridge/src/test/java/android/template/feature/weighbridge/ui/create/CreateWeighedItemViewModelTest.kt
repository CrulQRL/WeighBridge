package android.template.feature.weighbridge.ui.create

import android.template.feature.weighbridge.FakeWeighedItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateWeighedItemViewModelTest {

    @Test
    fun uiState_driverUpdated() = runTest {
        val viewModel = CreateWeighedItemViewModel(
            FakeWeighedItemRepository()
        )

        viewModel.setDriver("Budi")
        val item = viewModel.uiState.first()
        Assert.assertEquals("Budi", item.newDriver)
    }

    @Test
    fun uiState_licenseUpdated() = runTest {
        val viewModel = CreateWeighedItemViewModel(
            FakeWeighedItemRepository()
        )

        viewModel.setLicense("B7732BUK")
        val item = viewModel.uiState.first()
        Assert.assertEquals("B7732BUK", item.newLicense)
    }

    @Test
    fun uiState_inboundUpdated_valid() = runTest {
        val viewModel = CreateWeighedItemViewModel(
            FakeWeighedItemRepository()
        )

        viewModel.setInbound("0.5")
        val item = viewModel.uiState.first()
        Assert.assertEquals("0.5", item.newInbound)
        Assert.assertEquals(true, item.isValidInbound)
    }

    @Test
    fun uiState_inboundUpdated_inValid() = runTest {
        val viewModel = CreateWeighedItemViewModel(
            FakeWeighedItemRepository()
        )

        viewModel.setInbound("0.5,6")
        val item = viewModel.uiState.first()
        Assert.assertEquals("0.5,6", item.newInbound)
        Assert.assertEquals(false, item.isValidInbound)
    }

    @Test
    fun uiState_outboundUpdated_valid() = runTest {
        val viewModel = CreateWeighedItemViewModel(
            FakeWeighedItemRepository()
        )

        viewModel.setInbound("0.3")
        viewModel.setOutbound("0.5")
        val item = viewModel.uiState.first()
        Assert.assertEquals("0.5", item.newOutbound)
        Assert.assertEquals(true, item.isValidOutbound)
        Assert.assertEquals("0.2", item.netWeight)
    }

    @Test
    fun uiState_outboundUpdated_inValid() = runTest {
        val viewModel = CreateWeighedItemViewModel(
            FakeWeighedItemRepository()
        )

        viewModel.setInbound("0.3")
        viewModel.setOutbound("0.5,6")
        val item = viewModel.uiState.first()
        Assert.assertEquals("0.5,6", item.newOutbound)
        Assert.assertEquals(false, item.isValidOutbound)
        Assert.assertEquals(null, item.netWeight)
    }
}
