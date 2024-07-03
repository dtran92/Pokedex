package ui.favourite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.widget.AppTopBar

@OptIn(KoinExperimentalAPI::class)
@Composable
fun FavouriteScreen(viewModel: FavouriteViewModel = koinViewModel(), bottomBar: @Composable () -> Unit) {
    val data = viewModel.favouriteList.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppTopBar(title = "Favourite") },
        bottomBar = bottomBar
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {
            items(items = data.value, key = { elem -> elem.name }) { item ->
                Text(text = item.name)
            }
        }
    }
}