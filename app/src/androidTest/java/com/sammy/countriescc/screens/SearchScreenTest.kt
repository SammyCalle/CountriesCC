package com.sammy.countriescc.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.presentation.search.SearchScreen
import com.sammy.countriescc.presentation.search.SearchScreenUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleCountries = listOf(
        CountrySummary(name = "Peru", code = "PER"),
        CountrySummary(name = "Poland", code = "POL"),
    )

    private fun setScreen(
        query: String = "",
        uiState: SearchScreenUiState = SearchScreenUiState.Success(sampleCountries),
        onQueryChange: (String) -> Unit = {},
        onCountryClick: (String) -> Unit = {}
    ) {
        composeTestRule.setContent {
            SearchScreen(
                query = query,
                uiState = uiState,
                onQueryChange = onQueryChange,
                onCountryClick = onCountryClick
            )
        }
    }

    @Test
    fun loading_state_shows_CircularProgressIndicator() {
        setScreen(uiState = SearchScreenUiState.Loading)

        composeTestRule
            .onNodeWithTag("LoadingIndicator").assertIsDisplayed()
    }

    @Test
    fun error_message_shows_error_message() {
        setScreen(uiState = SearchScreenUiState.Error("Something went wrong"))

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun success_state_shows_country_list() {
        setScreen(uiState = SearchScreenUiState.Success(sampleCountries))

        composeTestRule.onNodeWithText("Peru").assertIsDisplayed()
        composeTestRule.onNodeWithText("Poland").assertIsDisplayed()

    }

    @Test
    fun empty_results_show_no_countries_found_message() {
        setScreen(
            query = "xyz",
            uiState = SearchScreenUiState.Success(emptyList())
        )

        composeTestRule.onNodeWithText("No countries found for \"xyz\"")
            .assertIsDisplayed()
    }

    @Test
    fun clear_button_is_visible_when_query_is_not_empty() {
        setScreen(query = "Per")

        composeTestRule
            .onNodeWithContentDescription("Clear")
            .assertIsDisplayed()
    }

    @Test
    fun clear_button_is_not_visible_when_query_is_empty() {
        setScreen(query = "")

        composeTestRule
            .onNodeWithContentDescription("Clear")
            .assertDoesNotExist()
    }

    @Test
    fun clicking_clear_button_calls_onQueryChange_with_empty_string() {
        var capturedQuery: String? = null

        setScreen(
            query = "Per",
            onQueryChange = { capturedQuery = it }
        )

        composeTestRule
            .onNodeWithContentDescription("Clear")
            .performClick()

        assert(capturedQuery == "")
    }

    @Test
    fun clicking_a_country_calls_onCountryClick_with_correct_code() {
        var clickedCode: String? = null

        setScreen(
            uiState = SearchScreenUiState.Success(sampleCountries),
            onCountryClick = { clickedCode = it }
        )

        composeTestRule
            .onNodeWithText("Peru")
            .performClick()

        assert(clickedCode == "PER")
    }


}