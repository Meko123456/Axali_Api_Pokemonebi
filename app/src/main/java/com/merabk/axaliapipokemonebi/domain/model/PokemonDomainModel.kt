package com.merabk.axaliapipokemonebi.domain.model

import com.merabk.axaliapipokemonebi.data.model.Sprites
import com.merabk.axaliapipokemonebi.data.model.Stats

data class PokemonDomainModel(
    val typeItem: List<String>,
    val name: String,
    val sprites: Sprites,
    val stats: List<String>,
    val height: Int,
    val weight: Int
)
