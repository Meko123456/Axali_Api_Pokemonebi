package com.merabk.axaliapipokemonebi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merabk.axaliapipokemonebi.domain.usecase.GetPokemonListUseCase
import com.merabk.axaliapipokemonebi.util.Constants.IMAGE_BEGINNING
import com.merabk.axaliapipokemonebi.util.Constants.IMAGE_END
import com.merabk.axaliapipokemonebi.util.Dispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val dispatchers: Dispatchers
) :
    ViewModel() {

    private val _allPokemonsData: MutableSharedFlow<DataState<List<PokedexListEntry>>> =
        MutableSharedFlow(replay = 2)
    val allPokemonData = _allPokemonsData.asSharedFlow()

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        dispatchers.launchBackground(viewModelScope) {
            _allPokemonsData.tryEmit(DataState.Loading)
            val result = getPokemonListUseCase()
            result.onSuccess { data ->
                val pokedexEntries = data.mapIndexed { _, entry ->
                    val number = if (entry.url.endsWith("/")) {
                        entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                    } else {
                        entry.url.takeLastWhile { it.isDigit() }
                    }
                    val url = "$IMAGE_BEGINNING${number}$IMAGE_END"
                    PokedexListEntry(entry.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }, url, number.toInt())
                }
                _allPokemonsData.tryEmit(DataState.Success(pokedexEntries))
            }
            result.onFailure {
                _allPokemonsData.tryEmit(DataState.Error(it.message ?: "An error occurred"))
            }
        }
    }
}