package com.toba.nick2905.kubatak.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ulos(
    var ulosBatak: String,
    var ulosAksara: String,
    var maknaBatak : String,
    var maknaIndonesia: String
) : Parcelable