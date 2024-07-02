package data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import data.local.model.PokemonEntity

@Dao
interface PokemonDao {
    @Insert
    suspend fun addPokemon(item: PokemonEntity)

    @Query("SELECT * FROM PokemonEntity")
    suspend fun getAllPokemon(): List<PokemonEntity>

    @Delete
    suspend fun deletePokemon(item: PokemonEntity)
}