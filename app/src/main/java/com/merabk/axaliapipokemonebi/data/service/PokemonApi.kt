package com.merabk.axaliapipokemonebi.data.service

import com.merabk.axaliapipokemonebi.data.model.Pokemon
import com.merabk.axaliapipokemonebi.data.model.PokemonListApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonListApiModel>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Response<Pokemon>
}