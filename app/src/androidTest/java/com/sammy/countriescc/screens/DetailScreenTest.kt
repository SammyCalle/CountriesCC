package com.sammy.countriescc.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.presentation.detail.DetailScreen
import com.sammy.countriescc.presentation.detail.DetailScreenUiState
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleCountry = CountryDetail(name = "Peru",
        coatOfArms = "https://mainfacts.com/media/images/coats_of_arms/pe.png",
        flag = "https://flagcdn.com/w320/pe.png",
        flagDescription = "Flag of Peru",
        currencyName = listOf("Peruvian sol"),
        capital = listOf("Lima"),
        population = 32971854,
        languages = listOf("Spanish"),
        continents = listOf("South America"),
        timezones = listOf("UTC-05:00"),
        googleMaps = "https://goo.gl/maps/uDWEUaujHZJFkXoY6")

    private fun setScreen(
        uiState: DetailScreenUiState = DetailScreenUiState.Success(sampleCountry),
        onBackClick: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            DetailScreen(
                uiState = uiState,
                onBackClick = onBackClick
            )
        }
    }

    @Test
    fun loading_state_shows_CircularProgressIndicator() {
        setScreen(uiState = DetailScreenUiState.Loading)
        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun error_message_shows_error_message() {
        setScreen(uiState = DetailScreenUiState.Error("Something went wrong"))

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun success_state_shows_country_details() {
        setScreen(uiState = DetailScreenUiState.Success(sampleCountry))

        composeTestRule.onNodeWithText("Peru").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lima").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("Spanish").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("32,971,854").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("South America").performScrollTo().assertIsDisplayed()

    }

    @Test
    fun back_button_calls_onBackClick() {
        var backClicked = false

        setScreen(onBackClick = { backClicked = true })

        composeTestRule.onNodeWithTag("BackIcon").performClick()

        assert(backClicked) { "onBackClick was not called" }

    }

    @Test
    fun success_state_shows_google_maps_link() {
        setScreen(uiState = DetailScreenUiState.Success(sampleCountry))

        composeTestRule.onRoot().performTouchInput { swipeUp() }

        composeTestRule
            .onNodeWithText("View on Google Maps")
            .assertIsDisplayed()
    }



}

