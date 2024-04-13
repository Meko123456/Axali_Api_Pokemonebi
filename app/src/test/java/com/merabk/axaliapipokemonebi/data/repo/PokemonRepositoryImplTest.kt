package com.merabk.axaliapipokemonebi.data.repo

import com.merabk.axaliapipokemonebi.data.mapper.ErrorMapper
import com.merabk.axaliapipokemonebi.data.mapper.NoInternetException
import com.merabk.axaliapipokemonebi.data.mapper.PokemonDetailsMapper
import com.merabk.axaliapipokemonebi.data.mapper.PokemonMapper
import com.merabk.axaliapipokemonebi.data.model.Pokemon
import com.merabk.axaliapipokemonebi.data.model.PokemonApiResult
import com.merabk.axaliapipokemonebi.data.model.PokemonListApiModel
import com.merabk.axaliapipokemonebi.data.model.Sprites
import com.merabk.axaliapipokemonebi.data.model.Stat
import com.merabk.axaliapipokemonebi.data.model.Stats
import com.merabk.axaliapipokemonebi.data.model.Type
import com.merabk.axaliapipokemonebi.data.model.TypeX
import com.merabk.axaliapipokemonebi.data.service.PokemonApi
import com.merabk.axaliapipokemonebi.domain.model.PokemonDomainModel
import com.merabk.axaliapipokemonebi.domain.model.PokemonMainPageModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.net.ConnectException

class PokemonRepositoryImplTest {

    @get:Rule
    val mockRule = MockKRule(this)

    @MockK
    private lateinit var mockService: PokemonApi

    @MockK
    private lateinit var mockPokemonMapper: PokemonMapper

    @MockK
    private lateinit var mockPokemonDetailsMapper: PokemonDetailsMapper

    @MockK
    private lateinit var mockErrorMapper: ErrorMapper

    @InjectMockKs
    private lateinit var repository: PokemonRepositoryImpl


    @Test
    fun testGetPokemonList() = runTest {
        val limit = 2
        val offset = 0
        val pokemonApiResult1 = PokemonApiResult("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1")
        val pokemonApiResult2 = PokemonApiResult("ivysaur", "https://pokeapi.co/api/v2/pokemon/2")
        val pokemonListResponse = PokemonListApiModel(
            count = 2,
            next = "https://pokeapi.co/api/v2/pokemon?offset=2&limit=0",
            previous = 0,
            results = listOf(pokemonApiResult1, pokemonApiResult2)
        )
        val pokemonMainPageModel1 =
            PokemonMainPageModel("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1")
        val pokemonMainPageModel2 =
            PokemonMainPageModel("ivysaur", "https://pokeapi.co/api/v2/pokemon/2")
        val mappedPokemonList =
            listOf(pokemonMainPageModel1, pokemonMainPageModel2) // Mocked mapped list of Pokemon
        val response = Response.success(pokemonListResponse)
        coEvery { mockService.getPokemonList(limit, offset) } returns response
        every { mockPokemonMapper.map(pokemonListResponse.results) } returns mappedPokemonList
        val result = repository.getPokemonList(limit, offset)
        assertEquals(mappedPokemonList, result.getOrThrow())
    }

    @Test
    fun testGetPokemonListErrorCase() = runTest {
        val limit = 10
        val offset = 0
        val exception = ConnectException()
        coEvery { mockService.getPokemonList(limit, offset) } throws exception
        every { mockErrorMapper.invoke(exception) } returns NoInternetException()
        val result = repository.getPokemonList(limit, offset)
        Assert.assertTrue(result.isFailure)
        Assert.assertTrue(result.exceptionOrNull() is NoInternetException)
    }


    @Test
    fun testGetPokemonInfo() = runTest {
        val pokemonName = "Pikachu"
        val typeX = TypeX(
            name = "electric",
            url = "https://pokeapi.co/api/v2/type/13/"
        )
        val type = Type(
            slot = 1,
            type = typeX
        )
        val sprites = Sprites(
            back_default = "https://example.com/pikachu_back_default.png",
            back_shiny = "https://example.com/pikachu_back_shiny.png",
            front_default = "https://example.com/pikachu_front_default.png",
            front_shiny = "https://example.com/pikachu_front_shiny.png"
        )
        val stats = Stats(
            base_stat = 35,
            effort = 0, // Adjust this as needed
            stat = Stat("hp", "https://pokeapi.co/api/v2/stat/1/")
        )
        val pokemon = Pokemon(
            types = listOf(type),
            sprites = sprites,
            name = "Pikachu",
            stats = listOf(stats),
            height = 4,
            weight = 60
        )
        val response = Response.success(pokemon)
        coEvery { mockService.getPokemonInfo(pokemonName) } returns response
        val mappedPokemonInfo = PokemonDomainModel(
            typeItem = listOf("electric"),
            name = "Pikachu",
            sprites = Sprites(
                back_default = "https://example.com/pikachu_back_default.png",
                back_shiny = "https://example.com/pikachu_back_shiny.png",
                front_default = "https://example.com/pikachu_front_default.png",
                front_shiny = "https://example.com/pikachu_front_shiny.png"
            ),
            stats = listOf("hp: 35"),
            height = 4,
            weight = 60
        )
        coEvery { mockService.getPokemonInfo(pokemonName) } returns response
        every { mockPokemonDetailsMapper.map(pokemon) } returns mappedPokemonInfo
        val result = repository.getPokemonInfo(pokemonName)
        assertEquals(mappedPokemonInfo, result.getOrThrow())
    }

    @Test
    fun testGetPokemonInfoErrorCase() = runTest {
        val pokemonName = "Pikachu"
        val exception = ConnectException()
        coEvery { mockService.getPokemonInfo(pokemonName) } throws exception
        every { mockErrorMapper.invoke(exception) } returns NoInternetException()
        val result = repository.getPokemonInfo(pokemonName)
        Assert.assertTrue(result.isFailure)
        Assert.assertTrue(result.exceptionOrNull() is NoInternetException)
    }
}