package di

import androidx.room.Room
import data.local.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        Room.databaseBuilder<Database>(
            name = NSHomeDirectory() + "/my_room.db",
            factory = { Database::class.instantiateImpl() }
    }
}