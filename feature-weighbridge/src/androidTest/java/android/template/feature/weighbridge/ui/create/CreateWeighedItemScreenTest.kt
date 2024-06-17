package android.template.feature.weighbridge.ui.create

import android.template.core.data.di.FakeWeighedItemRepository
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
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
    fun formItem_exists() {
        composeTestRule.onNodeWithText("Driver Name").assertExists()
        composeTestRule.onNodeWithText("License Number").assertExists()
        composeTestRule.onNodeWithText("Inbound").assertExists()
        composeTestRule.onNodeWithText("Outbound").assertExists()
        composeTestRule.onNodeWithText("Net Weight").assertExists()
    }

    @Test
    fun formItem_responding() {
        composeTestRule.onNodeWithContentDescription("DriverName").performTextInput("Toni")
        composeTestRule.onNodeWithContentDescription("DriverName").assert(hasText("Toni"))
        composeTestRule.onNodeWithContentDescription("LicenseNumber").performTextInput("Toni")
        composeTestRule.onNodeWithContentDescription("LicenseNumber").assert(hasText("Toni"))
    }

}
