package com.merabk.axaliapipokemonebi.domain.usecase

import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel

interface GetPokemonListUseCase {
    suspend operator fun invoke(): Result<List<PokemonMainPageModel>>
}

/*

class GetAllMoviesUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(): Result<List<AllMoviesDomainModel>> = repo.getAllMoviesList()
}*/

