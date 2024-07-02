package data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetail(
    val id: Long? = null,
    val name: String? = null,
    val sprites: Sprites? = null,
)

@Serializable
data class Other(
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtwork? = null,
)

@Serializable
data class Sprites(
    val other: Other? = null,
)

@Serializable
data class OfficialArtwork(
    @SerialName("front_default")
    val frontDefault: String? = null,

    @SerialName("front_shiny")
    val frontShiny: String? = null
)