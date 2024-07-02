package di

import androidx.room.Room
import data.local.Database
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        Room.databaseBuilder<Database>(
            context = androidContext(),
            name = androidContext().getDatabasePath("my_room.db").absolutePath
        )
    }
}