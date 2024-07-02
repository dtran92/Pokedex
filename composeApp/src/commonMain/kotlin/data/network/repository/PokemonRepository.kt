package data.network.repository

import data.network.Response
import data.network.model.Pokemon
import data.network.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun fetchPokemon(limit: Int? = 100, offset: Int? = 0): Flow<Response<List<Pokemon>>>

    fun fetchPokemonDetail(name: String): Flow<Response<PokemonDetail?>>
}