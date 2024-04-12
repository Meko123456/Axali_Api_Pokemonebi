package com.merabk.axaliapipokemonebi.domain.model

import com.google.gson.annotations.SerializedName
import com.merabk.axaliapipokemonebi.data.model.Ability
import com.merabk.axaliapipokemonebi.data.model.Form
import com.merabk.axaliapipokemonebi.data.model.GameIndice
import com.merabk.axaliapipokemonebi.data.model.Move
import com.merabk.axaliapipokemonebi.data.model.Species
import com.merabk.axaliapipokemonebi.data.model.Sprites
import com.merabk.axaliapipokemonebi.data.model.Stat
import com.merabk.axaliapipokemonebi.data.model.Type

data class PokemonDomeinModel(
    val abilities: List<Ability>,
    @SerializedName("base_experience") val baseExperience: Int,
    val forms: List<Form>,
    @SerializedName("game_indices") val gameIndices: List<GameIndice>,
    val height: Int,
    @SerializedName("held_items") val heldItems: List<Any>,
    val id: Int,
    @SerializedName("is_default") val isDefault: Boolean,
    @SerializedName("location_area_encounters") val locationAreaEncounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    @SerializedName("past_types") val pastTypes: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)
