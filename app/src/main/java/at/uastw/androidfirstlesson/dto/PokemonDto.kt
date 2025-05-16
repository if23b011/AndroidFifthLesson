package at.uastw.androidfirstlesson.dto

data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeDto>
)

data class PokemonTypeDto(
    val name: String
)
