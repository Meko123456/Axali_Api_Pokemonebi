package com.merabk.axaliapipokemonebi.data.repo

import com.merabk.axaliapipokemonebi.data.mapper.ErrorMapper
import com.merabk.axaliapipokemonebi.data.mapper.PokemonDetailsMapper
import com.merabk.axaliapipokemonebi.data.mapper.PokemonMapper
import com.merabk.axaliapipokemonebi.data.service.PokemonApi
import com.merabk.axaliapipokemonebi.domain.model.PokemonDomainModel
import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel
import com.merabk.axaliapipokemonebi.domain.repo.PokemonRepository
import com.merabk.axaliapipokemonebi.util.callAndMap
import com.merabk.axaliapipokemonebi.util.mapError
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: PokemonApi,
    private val pokemonMapper: PokemonMapper,
    private val pokemonDetailsMapper: PokemonDetailsMapper,
    private val errorMapper: ErrorMapper,
) : PokemonRepository {
    override suspend fun getPokemonList(
        limit: Int, //items from page
        offset: Int //page number
    ): Result<List<PokemonMainPageModel>> = callAndMap(
        serviceCall = {
            service.getPokemonList(limit, offset)
        },
        mapper = { response ->
            pokemonMapper.map(response.results)
        }
    ).mapError(errorMapper::invoke)

    override suspend fun getPokemonInfo(name: String): Result<PokemonDomainModel> = callAndMap(
        serviceCall = {
            service.getPokemonInfo(name)
        },
        mapper = { response ->
            pokemonDetailsMapper.map(response)
        }
    ).mapError(errorMapper::invoke)

}