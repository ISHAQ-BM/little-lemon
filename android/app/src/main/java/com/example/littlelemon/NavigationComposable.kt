package com.example.littlelemon

import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    menuItems: List<MenuItemRoom>,
    onSearch: (String) -> Unit,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    searchPhrase: String
){
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences by lazy {context.getSharedPreferences("User", MODE_PRIVATE)}
    Log.d("user","${sharedPreferences.contains("firstName")}")


    NavHost(
        navController = navController,
        startDestination = if (sharedPreferences.contains("firstName")) Home.route else Onboarding.route
    ){

        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            Home(navController,
                menuItems,
                onSearch,
                selectedCategory,
                onCategorySelected,
                searchPhrase)
        }
        composable(Profile.route) {
            Profile(navController= navController)
        }
    }
}