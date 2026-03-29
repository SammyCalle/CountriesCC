package com.sammy.countriescc.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sammy.countriescc.presentation.detail.DetailScreen
import com.sammy.countriescc.presentation.detail.DetailViewModel
import com.sammy.countriescc.presentation.search.SearchScreen
import com.sammy.countriescc.presentation.search.SearchViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ) {
        composable(Screen.SearchScreen.route) {
            val viewModel: SearchViewModel = hiltViewModel()
            val query by viewModel.query.collectAsStateWithLifecycle()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SearchScreen(
                query = query,
                uiState = uiState,
                onQueryChange = viewModel::onQueryChange,
                onCountryClick = { countryCode ->
                    navController.navigate(Screen.DetailScreen.createRoute(countryCode))
                }
            )
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = Screen.DetailScreen.arguments
        ) { backStackEntry ->
            val countryCode =
                backStackEntry.arguments?.getString("countryCode") ?: return@composable

            val viewModel: DetailViewModel = hiltViewModel()

            LaunchedEffect(countryCode) {
                viewModel.loadCountry(countryCode)
            }

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DetailScreen(uiState = uiState, onBackClick = { navController.popBackStack() })
        }
    }
}