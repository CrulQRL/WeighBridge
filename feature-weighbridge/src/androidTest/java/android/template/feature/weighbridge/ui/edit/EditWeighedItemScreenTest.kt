package android.template.feature.weighbridge.ui.edit

import android.template.core.data.di.FakeWeighedItemRepository
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditWeighedItemScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            EditWeighedItemScreen(
                rememberNavController(),
                EditWeighedItemViewModel(
                    SavedStateHandle().apply {
                        set("uid", 6L)
                    },
                    FakeWeighedItemRepository()
                )
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
    fun detailItem_exists() {
        composeTestRule.onNodeWithText("Komang").assertExists()
        composeTestRule.onNodeWithText("B6572CCA").assertExists()
        composeTestRule.onNodeWithText("0.3").assertExists()
        composeTestRule.onNodeWithText("0.7").assertExists()
        composeTestRule.onNodeWithText("0.4").assertExists()
    }

    @Test
    fun formItem_responding() {
        composeTestRule.onNodeWithContentDescription("DriverName").performTextClearance()
        composeTestRule.onNodeWithContentDescription("DriverName").performTextInput("Toni")
        composeTestRule.onNodeWithContentDescription("DriverName").assert(hasText("Toni"))
        composeTestRule.onNodeWithContentDescription("LicenseNumber").performTextClearance()
        composeTestRule.onNodeWithContentDescription("LicenseNumber").performTextInput("B1135CCA")
        composeTestRule.onNodeWithContentDescription("LicenseNumber").assert(hasText("B1135CCA"))
    }
}
