package com.merabk.axaliapipokemonebi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Sprites(
    val back_default: String,
    val back_shiny: String,
    val front_default: String,
    val front_shiny: String
)