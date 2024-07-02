import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import navigation.Navigation
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            Surface(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                val navBackStackEntry = navController.currentBackStackEntryAsState()
                val showBottomBar = rememberSaveable { mutableStateOf(true) }

                //TODO: Google will change compose navigation, need to update this later
                showBottomBar.value = when (navBackStackEntry.value?.destination?.route) {
                    "home", "favourite" -> true
                    else -> false
                }

                Scaffold {
                    Navigation(navController = navController, modifier = Modifier.padding(it))
                }
            }
        }
    }
}

