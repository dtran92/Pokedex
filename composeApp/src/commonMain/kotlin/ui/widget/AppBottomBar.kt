package ui.widget

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.painterResource
import pokedex.composeapp.generated.resources.*

@Composable
fun AppBottomBar(navController: NavController) {


    NavigationBar() {
        val currentDestination = navController.currentBackStackEntryAsState()
        val currentRoute = currentDestination.value?.destination?.route

        BottomNavigationItem.entries.forEach { item ->
            val isTabSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isTabSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.findStartDestination().route?.let {
                            popUpTo(it) {
                                saveState = false
                            }
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                icon = {
                    if (item == BottomNavigationItem.Home) {
                        if (!isTabSelected) {
                            Icon(
                                painterResource(Res.drawable.ic_home_outlined),
                                tint = Color(0xFF3F4857),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(Res.drawable.ic_home_filled),
                                tint = Color(0xFF3F4857),
                                contentDescription = null
                            )
                        }
                    } else {
                        if (!isTabSelected) {
                            Icon(
                                painterResource(Res.drawable.ic_heart),
                                tint = Color(0xFFFD525C),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(Res.drawable.ic_heart_filled),
                                tint = Color(0xFFFD525C),
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

//TODO: change implementation to type-safe args instead of string-based args
enum class BottomNavigationItem(val route: String) {
    Home("home"),
    Favourite("favourite")
}