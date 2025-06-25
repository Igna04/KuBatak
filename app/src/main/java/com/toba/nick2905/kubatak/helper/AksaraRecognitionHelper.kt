package com.toba.nick2905.kubatak.helper

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.toba.nick2905.kubatak.services.ApiClient

class AksaraRecognitionHelper(private val context: Context) {
    private val TAG = "AksaraRecognitionHelper"

    init {
        Log.d(TAG, "✅ AksaraRecognitionHelper siap menggunakan API Flask")
    }

    /**
     * Fungsi untuk mengirim gambar ke Flask dan menerima hasil prediksi aksara.
     * Hasil berupa pasangan: (aksara, confidence) yang dikirim ke callback.
     */
    fun recognizeCTC(bitmap: Bitmap, callback: (String, Float) -> Unit) {
        ApiClient.sendImage(bitmap) { result, confidence ->
            if (result != null && confidence != null) {
                callback(result, confidence)
            } else {
                callback("Gagal prediksi", 0f)
            }
        }
    }

    fun close() {
        // Tidak ada resource yang perlu ditutup karena tidak ada interpreter
        Log.d(TAG, "🧹 close() dipanggil - tidak ada resource untuk ditutup")
    }
}
