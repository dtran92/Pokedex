package ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import data.network.Response
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.widget.AppLoadingBar
import ui.widget.AppTopBar

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DetailScreen(name: String, viewModel: DetailViewModel = koinViewModel(), goBack: () -> Unit) {
    val showLoading = remember { mutableStateOf(false) }
    val item = viewModel.detail.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.getDetail(name).collectLatest {
            when (it) {
                is Response.Loading -> {
                    showLoading.value = true
                }

                is Response.Error -> {
                    showLoading.value = false
                    it.error?.message?.let { error ->
                        snackbarHostState.showSnackbar(error)
                    }
                }

                is Response.Success -> {
                    showLoading.value = false
                    viewModel.updateData(it.data)
                }

            }
        }
    }

    Scaffold(
        topBar = { AppTopBar(title = name.capitalize(Locale.current), canGoBack = true, goBack = goBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center) {
            item.value?.let {
                Column {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(it.sprites?.other?.officialArtwork?.frontDefault ?: "")
                            .memoryCachePolicy(CachePolicy.DISABLED)
                            .diskCachePolicy(CachePolicy.DISABLED)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        AppLoadingBar(showLoading = showLoading.value, modifier = Modifier.fillMaxSize())
    }

}