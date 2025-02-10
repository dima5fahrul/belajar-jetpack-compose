package com.example.submissionjetapackcompose.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.example.submissionjetapackcompose.R
import com.example.submissionjetapackcompose.model.Artist
import com.example.submissionjetapackcompose.ui.theme.SubmissionJetapackComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeArtist = Artist(
        id = 0,
        name = "me",
        photo = R.drawable.me,
        description = "description",
        isFavorite = true,
        place = "place"
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetapackComposeTheme {
                DetailContent(
                    photo = fakeArtist.photo,
                    title = fakeArtist.name,
                    description = fakeArtist.description,
                    place = fakeArtist.place,
                    onBackClick = {  },
                    isFavorite = fakeArtist.isFavorite,
                    onClick = {  },
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(fakeArtist.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeArtist.place).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeArtist.description).assertIsDisplayed()
    }

    @Test
    fun addFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun detailInformationIsScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun workingFavorite_status() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        val isFavorite = fakeArtist.isFavorite
        val expectedContentDescription = if (isFavorite) {
            "remove from favorite"
        } else {
            "add to favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button").assertContentDescriptionEquals(expectedContentDescription)
    }
}