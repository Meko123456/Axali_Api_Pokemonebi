package com.merabk.axaliapipokemonebi.di

import com.merabk.axaliapipokemonebi.data.mapper.ErrorMapper
import com.merabk.axaliapipokemonebi.data.mapper.PokemonMapper
import com.merabk.axaliapipokemonebi.data.repo.PokemonRepositoryImpl
import com.merabk.axaliapipokemonebi.domain.repo.PokemonRepository
import com.merabk.axaliapipokemonebi.util.Dispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SomeBindsModule {

    @Binds
    fun bindDispatchers(
        dispatchersImpl: Dispatchers.DispatchersImpl
    ): Dispatchers

    @Binds
    @Singleton
    fun bindMyRepository(
        myRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    fun bindAllFilmsMapper(
        pokemonMapper: PokemonMapper.PokemonMapperImpl
    ): PokemonMapper

    @Binds
    fun bindErrorMapper(impl: ErrorMapper.ErrorMapperImpl): ErrorMapper

}