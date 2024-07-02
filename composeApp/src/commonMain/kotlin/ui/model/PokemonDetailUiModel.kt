package ui.model


data class PokemonDetailUiModel(
    val id: Long? = null,
    val name: String? = null,
    val sprites: SpritesUiModel? = null,
)

data class OtherUiModel(
    val officialArtwork: OfficialArtworkUiModel? = null,
)

data class SpritesUiModel(
    val other: OtherUiModel? = null,
)

data class OfficialArtworkUiModel(
    val frontDefault: String? = null,
    val frontShiny: String? = null
)