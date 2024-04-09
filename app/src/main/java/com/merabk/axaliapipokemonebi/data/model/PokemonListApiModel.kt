package com.merabk.axaliapipokemonebi.data.model

//@Serializable
data class PokemonListApiModel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonApiResult>
)