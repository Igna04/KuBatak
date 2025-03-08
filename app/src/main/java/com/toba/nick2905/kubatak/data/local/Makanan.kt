package com.toba.nick2905.kubatak.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Makanan(
    var imgMakanan: Int,
    var nameMakanan: String,
    var aksaraMakanan: String,
    var descMakanan: String
) : Parcelable