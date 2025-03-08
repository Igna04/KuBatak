package com.toba.nick2905.kubatak.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kamus(
    var kamusBatak: String,
    var artiIndonesia: String
) : Parcelable