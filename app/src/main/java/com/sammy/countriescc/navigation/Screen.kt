package com.sammy.countriescc.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    object SearchScreen : Screen("search_screen")

    object DetailScreen : Screen("detail_screen/{countryCode}") {
        fun createRoute(countryCode: String) = "detail_screen/$countryCode"
        val arguments = listOf(
            navArgument("countryCode") { type = NavType.StringType }
        )
    }

}