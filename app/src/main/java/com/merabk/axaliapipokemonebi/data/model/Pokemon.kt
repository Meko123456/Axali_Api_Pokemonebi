package com.merabk.axaliapipokemonebi.data.model

data class Pokemon(
    val types: List<Type>,
    val sprites: Sprites,
    val name: String,
    val stats: List<Stats>,
    val height: Int,
    val weight: Int
)