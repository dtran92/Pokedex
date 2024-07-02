package data.network.repository

import data.network.Response
import data.network.model.Pokemon
import data.network.model.PokemonDetail
import data.network.model.PokemonResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class PokemonRepositoryImpl(private val httpClient: HttpClient) : PokemonRepository {
    override fun fetchPokemon(limit: Int?, offset: Int?) = flow<Response<List<Pokemon>>> {
        httpClient.get(url = Url("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$offset"))
            .body<PokemonResponse>()
            .apply {
                emit(Response.Success(this.results))
            }
    }.onStart { emit(Response.Loading()) }.catch { emit(Response.Error(error = it)) }

    override fun fetchPokemonDetail(name: String): Flow<Response<PokemonDetail?>> = flow<Response<PokemonDetail?>> {
        httpClient.get(url = Url("https://pokeapi.co/api/v2/pokemon/$name"))
            .body<PokemonDetail?>().apply {
                emit(Response.Success(data = this))
            }
    }.onStart { emit(Response.Loading()) }.catch { emit(Response.Error(error = it)) }
}