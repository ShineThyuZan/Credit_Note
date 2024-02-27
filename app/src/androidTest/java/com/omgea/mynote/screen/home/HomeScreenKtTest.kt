/*
package com.omgea.mynote.screen.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omgea.mynote.MainActivity
import com.omgea.mynote.di.AppModule
import com.omgea.mynote.graph.Destination
import com.omgea.mynote.graph.Routes
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
class HomeScreenKtTest {

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
                    startDestination = Destination.Home.route,
                    route = Routes.HOME
                ) {
                    composable(route = Routes.HOME) {
                        HomeScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Add New Note").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }
}*/
