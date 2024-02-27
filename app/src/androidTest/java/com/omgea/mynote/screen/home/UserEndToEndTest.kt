package com.omgea.mynote.screen.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omgea.mynote.MainActivity
import com.omgea.mynote.di.AppModule
import com.omgea.mynote.graph.Destination
import com.omgea.mynote.graph.Routes
import com.omgea.mynote.graph.SplashScreen
import com.omgea.mynote.graph.navGraph
import com.omgea.mynote.ui.theme.MyNoteTheme
import com.omgea.mynote.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class UserEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            MyNoteTheme {
                NavHost(
                    navController = navController,
                    startDestination = Destination.Splash.route,
                    route = Routes.ROOT_ROUTE
                ) {
                    composable(route = Destination.Splash.route) {
                        SplashScreen(navController = navController)
                    }
                    navGraph(navController = navController)
                }
            }
        }
    }

    @Test
    fun saveNewUser_editAfter() {
        composeRule.onNodeWithContentDescription("Add New Note").performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-content")
        composeRule.onNodeWithContentDescription("Add New Note").performClick()
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
        composeRule.onNodeWithText("test-title").performClick()

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("2")
        composeRule.onNodeWithContentDescription("Save").performClick()
        composeRule.onNodeWithText("test-title2").assertIsDisplayed()

    }
    @Test
    fun saveNewNotes_orderByTitleDescending(){
        for(i in 1..3){
            composeRule.onNodeWithContentDescription("Add New Note").performClick()
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule.onNodeWithContentDescription("Add New Note").performClick()
        }
        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add New Note").performClick()
        composeRule.onNodeWithContentDescription("Title").performClick()
        composeRule.onNodeWithContentDescription("Descending").performClick()

    }

}