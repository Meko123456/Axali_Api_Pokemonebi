package com.merabk.axaliapipokemonebi.data.usecase

import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel
import com.merabk.axaliapipokemonebi.domain.repo.PokemonRepository
import com.merabk.axaliapipokemonebi.domain.usecase.GetPokemonListUseCase
import com.merabk.axaliapipokemonebi.util.Constants
import com.merabk.axaliapipokemonebi.util.Constants.PAGE_SIZE
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetPokemonListUseCaseImpl @Inject constructor(private val repo: PokemonRepository) :
    GetPokemonListUseCase {
    override suspend fun invoke(): Result<List<PokemonMainPageModel>> = repo.getPokemonList(
        PAGE_SIZE, 0
    )
}