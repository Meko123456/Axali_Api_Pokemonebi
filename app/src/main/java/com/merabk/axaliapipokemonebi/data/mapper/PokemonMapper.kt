package com.merabk.axaliapipokemonebi.data.mapper

import com.merabk.axaliapipokemonebi.data.model.PokemonApiResult
import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel
import dagger.Reusable
import javax.inject.Inject

interface PokemonMapper {
    fun map(allMoviesModel: List<PokemonApiResult>): List<PokemonMainPageModel>

    @Reusable
    class PokemonMapperImpl @Inject constructor() : PokemonMapper {
        override fun map(allMoviesModel: List<PokemonApiResult>): List<PokemonMainPageModel> =
            allMoviesModel.map {
                PokemonMainPageModel(
                    name = it.name,
                    url = it.url
                )
            }
    }
}