package com.merabk.axaliapipokemonebi.domain.repo

import com.merabk.axaliapipokemonebi.domain.model.PokemonDomeinModel
import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel


interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Result<List<PokemonMainPageModel>>
    suspend fun getPokemonInfo(name: String): Result<PokemonDomeinModel>
}