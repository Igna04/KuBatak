package com.toba.nick2905.kubatak.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kebudayaan(
    var nameDetailBatak: String,
    var descDetailBatak: String
) : Parcelable