package ui.detail

import androidx.lifecycle.ViewModel
import data.network.Response
import data.network.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import ui.model.OfficialArtworkUiModel
import ui.model.OtherUiModel
import ui.model.PokemonDetailUiModel
import ui.model.SpritesUiModel

class DetailViewModel(private val repositoryImpl: PokemonRepository) : ViewModel() {
    private val _detail = MutableStateFlow<PokemonDetailUiModel?>(null)
    val detail = _detail.asStateFlow()

    fun getDetail(name: String): Flow<Response<PokemonDetailUiModel>> {
        return repositoryImpl.fetchPokemonDetail(name).map {
            when (it) {
                is Response.Loading -> Response.Loading()
                is Response.Error -> Response.Error(error = it.error)
                is Response.Success -> Response.Success(data = it.data.let { elem ->
                    PokemonDetailUiModel(
                        id = elem?.id,
                        name = elem?.name,
                        sprites = elem?.sprites?.let { sprite ->
                            SpritesUiModel(
                                other = OtherUiModel(
                                    officialArtwork = OfficialArtworkUiModel(
                                        frontDefault = sprite.other?.officialArtwork?.frontDefault,
                                        frontShiny = sprite.other?.officialArtwork?.frontShiny
                                    )
                                )
                            )
                        }
                    )
                })
            }
        }
    }

    fun updateData(data: PokemonDetailUiModel?) {
        _detail.value = data
    }
}