package com.myself.moviesappapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myself.moviesappapi.viewModel.MoviesViewModel
import com.myself.moviesappapi.views.DetailView
import com.myself.moviesappapi.views.HomeView

@Composable
fun NavManager(viewModel : MoviesViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Home"){
        composable("Home"){
            HomeView(viewModel,navController)
        }

        /*
        * composable("details/{movieId?}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt()
                DetailScreen(movieId = movieId)
           }
        * */
        composable("DetailView/{movieId}",
            arguments = listOf(
                navArgument("movieId"){type = NavType.IntType }
            )
        ){
            val id = it.arguments?.getInt("movieId") ?: 0
            DetailView(navController, viewModel, id)
        }
    }
}
