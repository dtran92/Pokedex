package navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ui.detail.DetailScreen
import ui.favourite.FavouriteScreen
import ui.home.HomeScreen
import ui.widget.AppBottomBar

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {

//    NavHost(navController = navController, startDestination = Screen.HomeScreen, modifier = modifier) {
//        composable<Screen.HomeScreen> {
//            Screen.HomeScreen()
//        }
//        composable<Screen.FavouriteScreen> {
//            Screen.FavouriteScreen()
//        }
//    }


    // TODO: Will switch to the above implementation as Google will stop using string-base route
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route, modifier = modifier) {
        composable(
            route = Screen.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            HomeScreen(
                bottomBar = { AppBottomBar(navController) } ,
                goToDetail = { name -> navController.navigate(route = Screen.DetailScreen.route + "/$name") }
            )
        }
        composable(
            route = Screen.FavouriteScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            FavouriteScreen(
                bottomBar = { AppBottomBar(navController) }
            )
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType }),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            DetailScreen(name = name ?: "", goBack = { navController.navigateUp() })
        }
    }
}