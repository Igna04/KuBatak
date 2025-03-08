package com.toba.nick2905.kubatak.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hari (
    var batakHari : String,
    var aksaraHari: String,
    var batakArtiHari: String,
    var indonesiaArtiHari: String
) : Parcelable