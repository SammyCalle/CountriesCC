package com.sammy.countriescc.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sammy.countriescc.domain.model.CountrySummary
import com.sammy.countriescc.ui.theme.CountriesCCTheme

@Composable
fun SearchScreen(
    query: String,
    uiState: SearchScreenUiState,
    onQueryChange: (String) -> Unit,
    onCountryClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth().testTag("SearchBar"),
            placeholder = { Text("Search countries…") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (uiState) {
            is SearchScreenUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag("LoadingIndicator")
                    )
                }
            }

            is SearchScreenUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(uiState.message)
                }
            }

            is SearchScreenUiState.Success -> {
                if (uiState.countries.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No countries found for \"$query\"")
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(uiState.countries, key = { it.code }) { country ->
                            CountryItem(country, onCountryClick = onCountryClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryItem(
    country: CountrySummary,
    onCountryClick: (String) -> Unit
) {
    Text(
        text = country.name,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCountryClick(country.code) }
            .padding(vertical = 12.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black
    )
    HorizontalDivider()
}

private val sampleCountries = listOf(
    CountrySummary(name = "Peru", code = "PE"),
    CountrySummary(name = "Poland", code = "PL"),
    CountrySummary(name = "Portugal", code = "PT"),
    CountrySummary(name = "Panama", code = "PA"),
    CountrySummary(name = "Pakistan", code = "PK"),
    CountrySummary(name = "Paraguay", code = "PY"),
)

@Preview(name = "Phone Portrait", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun SearchScreenPhonePortraitPreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "P",
            uiState = SearchScreenUiState.Success(sampleCountries),
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}

@Preview(
    name = "Phone Landscape",
    showBackground = true,
    device = "spec:width=914dp,height=411dp,dpi=420"
)
@Composable
fun SearchScreenPhoneLandscapePreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "P",
            uiState = SearchScreenUiState.Success(sampleCountries),
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}

@Preview(name = "Tablet Portrait", showBackground = true, device = Devices.PIXEL_TABLET)
@Composable
fun SearchScreenTabletPortraitPreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "P",
            uiState = SearchScreenUiState.Success(sampleCountries),
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}

@Preview(
    name = "Tablet Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun SearchScreenTabletLandscapePreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "P",
            uiState = SearchScreenUiState.Success(sampleCountries),
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}

@Preview(name = "Loading State", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun SearchScreenLoadingPreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "",
            uiState = SearchScreenUiState.Loading,
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}

@Preview(name = "Error State", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun SearchScreenErrorPreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "",
            uiState = SearchScreenUiState.Error("Something went wrong"),
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}

@Preview(name = "Empty State", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun SearchScreenEmptyPreview() {
    CountriesCCTheme {
        SearchScreen(
            query = "xyz",
            uiState = SearchScreenUiState.Success(emptyList()),
            onQueryChange = {},
            onCountryClick = {}
        )
    }
}