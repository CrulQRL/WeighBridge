package android.template.feature.weighbridge.ui.detail

import android.template.core.data.di.FakeWeighedItemRepository
import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailWeighedItemScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setup() {
        composeTestRule.setContent {

            val viewModel = DetailWeighedItemViewModel(
                SavedStateHandle().apply {
                    set("uid", 6L)
                },
                FakeWeighedItemRepository()
            )

            DetailWeighedItemScreen(
                rememberNavController(),
                viewModel
            )
        }
    }
    @Test
    fun detailItem_exists() {
        composeTestRule.onNodeWithText("Komang").assertExists()
        composeTestRule.onNodeWithText("B6572CCA").assertExists()
        composeTestRule.onNodeWithText("16 June 2024 08:58").assertExists()
        composeTestRule.onNodeWithText("0.3 Tons").assertExists()
        composeTestRule.onNodeWithText("0.7 Tons").assertExists()
        composeTestRule.onNodeWithText("0.4 Tons").assertExists()
    }
}
