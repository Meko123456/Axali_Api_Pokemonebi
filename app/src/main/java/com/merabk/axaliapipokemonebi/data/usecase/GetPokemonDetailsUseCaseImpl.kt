package com.merabk.axaliapipokemonebi.data.usecase

import com.merabk.axaliapipokemonebi.domain.model.PokemonDomeinModel
import com.merabk.axaliapipokemonebi.domain.repo.PokemonRepository
import com.merabk.axaliapipokemonebi.domain.usecase.GetPokemonDetailsUseCase
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPokemonDetailsUseCaseImpl @Inject constructor(private val repo: PokemonRepository) :
    GetPokemonDetailsUseCase {
    override suspend fun invoke(name: String): Result<PokemonDomeinModel> = repo.getPokemonInfo(
        name = name
    )
}