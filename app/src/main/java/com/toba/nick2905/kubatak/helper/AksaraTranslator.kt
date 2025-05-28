package com.toba.nick2905.kubatak.helper

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toba.nick2905.kubatak.R
import com.toba.nick2905.kubatak.data.local.Kamus
import java.io.BufferedReader
import java.io.InputStreamReader

class AksaraTranslator(private val context: Context) {
    private val TAG = "AksaraTranslator"
    private var kamusList: List<Kamus> = emptyList()

    init {
        loadKamusFromRaw()
    }

    private fun loadKamusFromRaw() {
        try {
            val inputStream = context.resources.openRawResource(R.raw.kamus)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val type = object : TypeToken<List<Kamus>>() {}.type
            kamusList = Gson().fromJson(reader, type)
            reader.close()
        } catch (e: Exception) {
            Log.e(TAG, "Gagal memuat kamus.json dari raw: ${e.message}")
        }
    }

    /** Menerjemahkan string Batak (maks. 2 kata, sesuai model) ke bahasa Indonesia */
    fun translateAksara(detectedAksara: String): Pair<String, String> {
        val input = detectedAksara.trim().lowercase()

        val match = kamusList.find { it.kamusBatak.lowercase() == input }

        return if (match != null) {
            Pair(match.kamusBatak, match.artiIndonesia)
        } else {
            Pair(input, "Tidak ditemukan dalam kamus")
        }
    }
}