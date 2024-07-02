package ui.model

data class PokemonUiModel(
    val name: String,
    val url: String,
    val isFavourite: Boolean = false
)