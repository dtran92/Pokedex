package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.dao.PokemonDao
import data.local.model.PokemonEntity
import data.network.Response
import data.network.repository.PokemonRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.model.PokemonUiModel

class HomeViewModel(
    private val repository: PokemonRepositoryImpl,
    private val pokemonDao: PokemonDao
) : ViewModel() {

    private val _dataList = MutableStateFlow<List<PokemonUiModel>>(emptyList())
    private val _localList = MutableStateFlow<List<PokemonUiModel>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _localList.value = pokemonDao.getAllPokemon().map { elem ->
                PokemonUiModel(elem.name, elem.url)
            }
        }
    }

    val pokemonFlow = repository.fetchPokemon().map {
        when (it) {
            is Response.Loading -> Response.Loading()
            is Response.Error -> Response.Error(error = it.error)
            is Response.Success -> Response.Success(
                data = it.data?.map { elem -> PokemonUiModel(elem.name, elem.url) }
            )
        }
    }.flowOn(Dispatchers.IO)

    val combineFlow = combine(_dataList, _localList) { dataList, localList ->
        dataList.map { elem -> if (elem in localList) elem.copy(isFavourite = true) else elem }
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun updateData(newData: List<PokemonUiModel>) {
        _dataList.value = newData
    }

    fun addToFavourite(item: PokemonUiModel) {
        println("add")
        viewModelScope.launch(Dispatchers.IO) {
            pokemonDao.addPokemon(PokemonEntity(name = item.name, url = item.url))
            _localList.update {
                it.toMutableList().apply { add(item) }
            }
        }
    }

    fun removeFromFavourite(item: PokemonUiModel) {
        println("remove")
        viewModelScope.launch(Dispatchers.IO) {
            pokemonDao.deletePokemon(PokemonEntity(name = item.name, url = item.url))
            _localList.update {
                it.toMutableList().apply { remove(item) }
            }
        }
    }


}

