package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import data.local.dao.PokemonDao
import data.local.model.PokemonEntity

@Database(exportSchema = true, entities = [PokemonEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}