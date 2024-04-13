package com.merabk.axaliapipokemonebi.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Stats(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
)