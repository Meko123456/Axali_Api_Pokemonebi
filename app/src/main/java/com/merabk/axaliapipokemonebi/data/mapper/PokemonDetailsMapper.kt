package com.merabk.axaliapipokemonebi.data.mapper

import com.merabk.axaliapipokemonebi.data.model.Pokemon
import com.merabk.axaliapipokemonebi.domain.model.PokemonDomainModel
import dagger.Reusable
import javax.inject.Inject

interface PokemonDetailsMapper {
    fun map(allMoviesModel: Pokemon): PokemonDomainModel

    @Reusable
    class PokemonMapperImpl @Inject constructor() : PokemonDetailsMapper {

        override fun map(allMoviesModel: Pokemon): PokemonDomainModel =
            PokemonDomainModel(
                height = allMoviesModel.height,
                sprites = allMoviesModel.sprites,
                stats = allMoviesModel.stats.map {
                    it.base_stat.toString()
                },
                weight = allMoviesModel.weight,
                name = allMoviesModel.name,
                typeItem = allMoviesModel.types.map {
                    it.type.name.orEmpty()
                }
            )

    }
}