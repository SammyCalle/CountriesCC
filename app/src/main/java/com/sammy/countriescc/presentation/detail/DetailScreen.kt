package com.sammy.countriescc.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sammy.countriescc.domain.model.CountryDetail
import com.sammy.countriescc.ui.theme.CountriesCCTheme

@Composable
fun DetailScreen(
    uiState: DetailScreenUiState,
    onBackClick: () -> Unit
) {
    when (uiState) {
        is DetailScreenUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailScreenUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(uiState.message)
            }
        }

        is DetailScreenUiState.Success -> {
            DetailContent(uiState.country, onBackClick = onBackClick)
        }
    }
}

@Composable
private fun DetailContent(country: CountryDetail, onBackClick: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    val windowInsets = WindowInsets.navigationBars
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(
            bottom = windowInsets.asPaddingValues().calculateBottomPadding() + 24.dp
        )
    ) {

        item {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }
        item {
            AsyncImage(
                model = country.flag,
                contentDescription = country.flagDescription,
                placeholder = ColorPainter(Color.LightGray),
                error = ColorPainter(Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
                text = country.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }

        item {
            AsyncImage(
                model = country.coatOfArms,
                contentDescription = "Coat of Arms",
                placeholder = ColorPainter(Color.LightGray),
                error = ColorPainter(Color.LightGray),
                modifier = Modifier
                    .size(80.dp)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            DetailSection(
                title = "Capital",
                value = country.capital.joinToString(", ")
            )
            DetailSection(
                title = "Population",
                value = "%,d".format(country.population)
            )
            DetailSection(
                title = "Continent",
                value = country.continents.joinToString(", ")
            )
            DetailSection(
                title = "Currency",
                value = country.currencyName.joinToString(", ")
            )
            DetailSection(
                title = "Languages",
                value = country.languages.joinToString(", ")
            )
            DetailSection(
                title = "Timezones",
                value = country.timezones.joinToString(", ")
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "View on Google Maps",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { uriHandler.openUri(country.googleMaps) },
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
private fun DetailSection(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}

private val sampleCountry = CountryDetail(
    name = "Peru",
    coatOfArms = "https://mainfacts.com/media/images/coats_of_arms/pe.png",
    flag = "https://flagcdn.com/w320/pe.png",
    flagDescription = "Flag of Peru",
    currencyName = listOf("Peruvian sol"),
    capital = listOf("Lima"),
    population = 32971854,
    languages = listOf("Spanish"),
    continents = listOf("South America"),
    timezones = listOf("UTC-05:00"),
    googleMaps = "https://goo.gl/maps/uDWEUaujHZJFkXoY6"
)


@Preview(name = "Detail Phone Portrait", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun DetailScreenPhonePortraitPreview() {
    CountriesCCTheme {
        DetailScreen(uiState = DetailScreenUiState.Success(sampleCountry), onBackClick = {})
    }
}


@Preview(
    name = "Detail Phone Landscape",
    showBackground = true,
    device = "spec:width=914dp,height=411dp,dpi=420"
)
@Composable
fun DetailScreenPhoneLandscapePreview() {
    CountriesCCTheme {
        DetailScreen(uiState = DetailScreenUiState.Success(sampleCountry),onBackClick = {})
    }
}


@Preview(name = "Detail Tablet Portrait", showBackground = true, device = Devices.PIXEL_TABLET)
@Composable
fun DetailScreenTabletPortraitPreview() {
    CountriesCCTheme {
        DetailScreen(uiState = DetailScreenUiState.Success(sampleCountry),onBackClick = {})
    }
}


@Preview(
    name = "Detail Tablet Landscape",
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun DetailScreenTabletLandscapePreview() {
    CountriesCCTheme {
        DetailScreen(uiState = DetailScreenUiState.Success(sampleCountry),onBackClick = {})
    }
}

@Preview(name = "Detail Loading", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun DetailScreenLoadingPreview() {
    CountriesCCTheme {
        DetailScreen(uiState = DetailScreenUiState.Loading,onBackClick = {})
    }
}


@Preview(name = "Detail Error", showBackground = true, device = Devices.PIXEL_6)
@Composable
fun DetailScreenErrorPreview() {
    CountriesCCTheme {
        DetailScreen(uiState = DetailScreenUiState.Error("Failed to load country details"),onBackClick = {})
    }
}