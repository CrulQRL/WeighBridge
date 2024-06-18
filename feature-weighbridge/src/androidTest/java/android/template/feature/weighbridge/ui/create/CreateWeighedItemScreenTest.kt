package android.template.feature.weighbridge.ui.create

import android.template.core.data.di.FakeWeighedItemRepository
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateWeighedItemScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            CreateWeighedItemScreen(
                rememberNavController(),
                CreateWeighedItemViewModel(FakeWeighedItemRepository())
            )
        }
    }
    @Test
    fun formItem_existsAndResponding() {
        composeTestRule.onNodeWithText("Driver Name").assertExists()
        composeTestRule.onNodeWithText("License Number").assertExists()
        composeTestRule.onNodeWithText("Inbound").assertExists()
        composeTestRule.onNodeWithText("Outbound").assertExists()
        composeTestRule.onNodeWithText("Net Weight").assertExists()

        composeTestRule.onNodeWithContentDescription("DriverName").performTextInput("Toni")
        composeTestRule.onNodeWithContentDescription("DriverName").assert(hasText("Toni"))
        composeTestRule.onNodeWithContentDescription("LicenseNumber").performTextInput("B1135CCA")
        composeTestRule.onNodeWithContentDescription("LicenseNumber").assert(hasText("B1135CCA"))
    }

    @Test
    fun formItem_submit() {
        composeTestRule.onNodeWithContentDescription("DriverName").performTextInput("Toni")
        composeTestRule.onNodeWithContentDescription("LicenseNumber").performTextInput("B1135CCA")
        composeTestRule.onNodeWithContentDescription("NewInbound").performTextInput("0.3")
        composeTestRule.onNodeWithContentDescription("NewOutbound").performTextInput("0.7")
        composeTestRule.onNodeWithText("0.4").assertExists()
        composeTestRule.onNodeWithText("Submit").assertIsEnabled()
    }

    @Test
    fun formItem_invalid() {
        composeTestRule.onNodeWithContentDescription("NewInbound").performTextInput("0.3")
        composeTestRule.onNodeWithContentDescription("NewOutbound").performTextInput("0.1")
        composeTestRule.onNodeWithText("Submit").assertIsNotEnabled()
        composeTestRule.onNodeWithText("-").assertExists()
    }
}
