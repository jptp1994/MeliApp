package com.example.meliapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meliapp.presentation.ui.screen.ProductDetailScreen
import com.example.meliapp.presentation.ui.screen.SearchScreen

@Composable
fun MeliAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Search.route) {
        composable(Screen.Search.route) {
            SearchScreen(
                onProductClick = { itemId ->
                    navController.navigate(Screen.ProductDetail.createRoute(itemId))
                }
            )
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) {
            ProductDetailScreen()
        }
    }
}

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object ProductDetail : Screen("product_detail/{itemId}") {
        fun createRoute(itemId: String) = "product_detail/$itemId"
    }
}