package com.example.mycomposetesting.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.mycomposetesting.R.string
import com.example.mycomposetesting.ui.theme.MyComposeTestingTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CalculatorAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyComposeTestingTheme {
                CalculatorApp()
            }
        }
    }

    @Test
    fun calculate_area_of_rectangle_correct() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.enter_length)).performTextInput("3")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.enter_width)).performTextInput("4")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.count)).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.result, 12.0)).assertExists()
    }
}