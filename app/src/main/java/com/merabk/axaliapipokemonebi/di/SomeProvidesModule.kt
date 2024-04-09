package com.merabk.axaliapipokemonebi.di

import com.merabk.axaliapipokemonebi.data.usecase.GetPokemonListUseCaseImpl
import com.merabk.axaliapipokemonebi.domain.usecase.GetPokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SomeProvidesModule {

    @Provides
    fun bindGetAllMoviesUseCase(
        getAllMoviesUseCase: GetPokemonListUseCaseImpl
    ): GetPokemonListUseCase = getAllMoviesUseCase

}