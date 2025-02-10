package com.example.submissionjetapackcompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.submissionjetapackcompose.model.ArtistData
import com.example.submissionjetapackcompose.navigation.Screen
import com.example.submissionjetapackcompose.ui.theme.SubmissionJetapackComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class JetArtistAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionJetapackComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetArtistApp(
                    navController = navController
                )
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    // klik item pada lazy grid, cek apakah item yang dituju sesuai dengan yang diharapkan
    @Test
    fun navHost_navigateToDetailScreen() {
        composeTestRule.onNodeWithTag("lazy_grid").performScrollToIndex(5)
        composeTestRule.onNodeWithText(ArtistData.artistData[5].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)
        composeTestRule.onNodeWithText(ArtistData.artistData[5].name).assertIsDisplayed()
    }

    // navigasi antar screen, cek apakah screen yang dituju sesuai dengan yang diharapkan
    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.favorites).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    // navigasi ke halaman about, cek apakah data yang ditampilkan sesuai dengan yang diharapkan
    @Test
    fun navigateTo_AboutPage() {
        composeTestRule.onNodeWithStringId(R.string.about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.dimas_fahrul).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.dfahrul07_gmail_com).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.dimasfahrul_my_id).assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang salah, cek apakah home screen berisi text not found
    @Test
    fun searchShowEmptyGridArtist() {
        val incorrectSearch = "aa31z"
        composeTestRule.onNodeWithStringId(R.string.search_artist).performTextInput(incorrectSearch)
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang benar, cek apakah data yang dicari ada di grid
    @Test
    fun searchShowGrdArtist() {
        val rightSearch = "martin"
        composeTestRule.onNodeWithStringId(R.string.search_artist).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("martin").assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang salah, cek apakah favorite screen berisi text not found
    @Test
    fun searchShowEmptyGridFavoriteArtist() {
        composeTestRule.onNodeWithStringId(R.string.favorites).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        val incorrectSearch = "aa31z"
        composeTestRule.onNodeWithStringId(R.string.search_artist).performTextInput(incorrectSearch)
        composeTestRule.onNodeWithTag("empty_favorite").assertIsDisplayed()
    }

    // melakukan pencarian dengan keyword yang benar, cek apakah data yang dicari ada di grid favorite
    @Test
    fun searchShowGrdFavoriteArtist() {
        val rightSearch = "martin"
        composeTestRule.onNodeWithStringId(R.string.search_artist).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("martin").assertIsDisplayed()
    }

    // Klik favorite di detail screen, cek apakah data favorite tersedia di favorite screen
    @Test
    fun favoriteClickInDetailScreen_ShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(ArtistData.artistData[1].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("back_home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.favorites).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        val rightSearch = "blinders"
        composeTestRule.onNodeWithStringId(R.string.search_artist).performTextInput(rightSearch)
        composeTestRule.onNodeWithText("blinders").assertIsDisplayed()
    }

    // Klik favorite dan delete favorite di detail screen, cek apakah data tidak ada di favorite screen
    @Test
    fun favoriteClickAndDeleteFavoriteInDetailScreen_NotShowInFavoriteScreen() {
        composeTestRule.onNodeWithText(ArtistData.artistData[0].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailArtist.route)
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("favorite_detail_button").performClick()
        composeTestRule.onNodeWithTag("back_home").performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.favorites).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        val rightSearch = "aspyer"
        composeTestRule.onNodeWithStringId(R.string.search_artist).performTextInput(rightSearch)
        composeTestRule.onNodeWithTag("empty_favorite").assertIsDisplayed()
    }
}