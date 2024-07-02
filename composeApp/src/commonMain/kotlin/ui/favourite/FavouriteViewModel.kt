package ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.local.dao.PokemonDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.model.PokemonUiModel

class FavouriteViewModel(private val pokemonDao: PokemonDao) : ViewModel() {
    private val _favouriteList = MutableStateFlow<List<PokemonUiModel>>(emptyList())
    val favouriteList = _favouriteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteList.value =
                pokemonDao.getAllPokemon().map { elem -> PokemonUiModel(name = elem.name, url = elem.url) }
        }
    }

}