package navigation

import kotlinx.serialization.Serializable

//@Serializable
//sealed class Screen {
//    @Serializable
//    data object HomeScreen : Screen()
//
//    @Serializable
//    data object FavouriteScreen : Screen()
//}

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home")
    data object FavouriteScreen : Screen("favourite")
    data object DetailScreen : Screen("detail")

}