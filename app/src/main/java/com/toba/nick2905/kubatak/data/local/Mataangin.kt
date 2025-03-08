package com.toba.nick2905.kubatak.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mataangin (
    var namaBatakMataangin: String,
    var namaAksaraMataangin: String,
    var namaBahasaMataangin: String
):Parcelable