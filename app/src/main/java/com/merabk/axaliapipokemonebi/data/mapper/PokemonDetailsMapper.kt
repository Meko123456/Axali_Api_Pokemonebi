package com.merabk.axaliapipokemonebi.data.mapper

import com.merabk.axaliapipokemonebi.data.model.Pokemon
import com.merabk.axaliapipokemonebi.domain.model.PokemonDomeinModel
import dagger.Reusable
import javax.inject.Inject

interface PokemonDetailsMapper {
    fun map(allMoviesModel: Pokemon): PokemonDomeinModel

    @Reusable
    class PokemonMapperImpl @Inject constructor() : PokemonDetailsMapper {

        override fun map(allMoviesModel: Pokemon): PokemonDomeinModel =
            PokemonDomeinModel(
                abilities = allMoviesModel.abilities,
                baseExperience = allMoviesModel.baseExperience,
                forms = allMoviesModel.forms,
                gameIndices = allMoviesModel.gameIndices,
                height = allMoviesModel.height,
                heldItems = allMoviesModel.heldItems,
                id = allMoviesModel.id,
                isDefault = allMoviesModel.isDefault,
                locationAreaEncounters = allMoviesModel.locationAreaEncounters,
                moves = allMoviesModel.moves,
                name = allMoviesModel.name,
                order = allMoviesModel.order,
                pastTypes = allMoviesModel.pastTypes,
                species = allMoviesModel.species,
                sprites = allMoviesModel.sprites,
                stats = allMoviesModel.stats,
                types = allMoviesModel.types,
                weight = allMoviesModel.weight
            )

    }
}