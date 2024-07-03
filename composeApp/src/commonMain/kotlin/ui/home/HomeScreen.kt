package ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import data.network.Response
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import pokedex.composeapp.generated.resources.Res
import pokedex.composeapp.generated.resources.ic_heart
import pokedex.composeapp.generated.resources.ic_heart_filled
import ui.widget.AppLoadingBar
import ui.widget.AppTopBar

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    bottomBar: @Composable () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
    goToDetail: (String) -> Unit
) {
    val data = viewModel.combineFlow.collectAsStateWithLifecycle()
    val showLoading = remember { mutableStateOf(false) }

    val state = rememberLazyGridState()

    LaunchedEffect(state.canScrollForward) {
        if (!state.canScrollForward)
            viewModel.loadMoreData().collectLatest {
                when (it) {
                    is Response.Loading -> {
                        showLoading.value = true
                    }

                    is Response.Error -> {
                        showLoading.value = false
                    }

                    is Response.Success -> {
                        showLoading.value = false
                        viewModel.addData(it.data ?: emptyList())
                    }
                }
            }
    }


    LaunchedEffect(Unit) {
        viewModel.pokemonFlow.collectLatest {
            when (it) {
                is Response.Loading -> {
                    showLoading.value = true
                }

                is Response.Error -> {
                    showLoading.value = false
                }

                is Response.Success -> {
                    showLoading.value = false
                    if (data.value.isEmpty()) viewModel.updateData(it.data ?: emptyList())
                }
            }
        }
    }

    Scaffold(
        topBar = { AppTopBar("Home") },
        bottomBar = bottomBar
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                state = state,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                items(items = data.value, key = { elem -> elem.name }) { item ->
                    val isFavorite = rememberSaveable { mutableStateOf(item.isFavourite) }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.sizeIn(minHeight = 70.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color.LightGray.copy(alpha = 0.5F))
                            .clickable {
                                goToDetail.invoke(item.name)
                            }
                            .padding(horizontal = 10.dp)

                    ) {
                        Text(item.name.capitalize(Locale.current), modifier = Modifier.weight(1F))
                        if (isFavorite.value) {
                            IconButton(onClick = {
                                isFavorite.value = false
                                viewModel.removeFromFavourite(item)
                            }) {
                                Icon(
                                    painterResource(Res.drawable.ic_heart_filled),
                                    tint = Color.Red,
                                    contentDescription = null
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                isFavorite.value = true
                                viewModel.addToFavourite(item)
                            }) {
                                Icon(
                                    painterResource(Res.drawable.ic_heart),
                                    tint = Color.Red,
                                    contentDescription = null
                                )
                            }
                        }

                    }
                }
            }

            AppLoadingBar(showLoading = showLoading.value, modifier = Modifier.fillMaxSize())
        }
    }
}