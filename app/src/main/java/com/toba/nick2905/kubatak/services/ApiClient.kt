// com/toba/nick2905/kubatak/network/ApiClient.kt
package com.toba.nick2905.kubatak.services

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody

object ApiClient {
    private val client = OkHttpClient()
    private const val TAG = "ApiClient"
    private const val BASE_URL = "http://192.168.189.26:5000/predict"

    fun sendImage(bitmap: android.graphics.Bitmap, callback: (String?, Float?) -> Unit) {
        // Simpan bitmap ke file sementara
        val tempFile = File.createTempFile("aksara", ".png")
        val fos = FileOutputStream(tempFile)
        bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                tempFile.name,
                tempFile.asRequestBody("image/*".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Request failed: ${e.message}")
                callback(null, null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val json = response.body?.string()
                    val jsonObject = JSONObject(json)
                    val predicted = jsonObject.getString("predicted_char")
                    val confidence = jsonObject.getDouble("confidence").toFloat()
                    callback(predicted, confidence)
                } else {
                    Log.e(TAG, "Server error: ${response.message}")
                    callback(null, null)
                }
            }
        })
    }
}
