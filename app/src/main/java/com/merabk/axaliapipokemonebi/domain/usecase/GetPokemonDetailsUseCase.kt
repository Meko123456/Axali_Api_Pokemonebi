package com.merabk.axaliapipokemonebi.domain.usecase

import com.merabk.axaliapipokemonebi.domain.model.PokemonDomainModel

interface GetPokemonDetailsUseCase {
    suspend operator fun invoke(name: String): Result<PokemonDomainModel>
}

/*

class GetAllMoviesUseCase @Inject constructor(private val repo: MoviesRepository) {
    suspend operator fun invoke(): Result<List<AllMoviesDomainModel>> = repo.getAllMoviesList()
}*/

