package di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.Database
import data.network.repository.PokemonRepositoryImpl
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.detail.DetailViewModel
import ui.favourite.FavouriteViewModel
import ui.home.HomeViewModel

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(message, null, "HTTP Client")
                    }
                }
                level = LogLevel.BODY
            }
        }.also { Napier.base(DebugAntilog()) }
    }
    single {
        PokemonRepositoryImpl(get())
    }


}

expect val platformModule: Module

val databaseModule = module {
    single {
        get<RoomDatabase.Builder<Database>>()
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single {
        get<Database>().pokemonDao()
    }
}

val appModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}