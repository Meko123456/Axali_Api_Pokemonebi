package com.merabk.axaliapipokemonebi.domain.usecase

import com.merabk.axaliapipokemonebi.domain.model.PokemonDomeinModel

interface GetPokemonDetailsUseCase {
    suspend operator fun invoke(name: String): Result<PokemonDomeinModel>
}

/*

class GetAllMoviesUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(): Result<List<AllMoviesDomainModel>> = repo.getAllMoviesList()
}*/

