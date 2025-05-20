package com.toba.nick2905.kubatak.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import org.tensorflow.lite.Interpreter

class AksaraRecognitionHelper(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val TAG = "AksaraRecognitionHelper"
    private val IMAGE_HEIGHT = 64
    private val IMAGE_WIDTH = 128
    private val NUM_CLASSES = 101 
    private val MODEL_PATH = "model_crnn.tflite"

    private val labels = arrayOf("a", "b", "ba", "be", "bi", "bo", "bu", "d", "da", "de", "di", "do", "du", "e", "g", "ga", "ge", "gi", "go", "gu", "ha-ka", "he-ke", "hi-ki", "h-k", "ho-ko", "hu-ku", "ta", "te")

    init {
        try {
            interpreter = Interpreter(loadModelFile())
            Log.d(TAG, "Model TFLite berhasil dimuat")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading model: ${e.message}")
        }
    }

    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(MODEL_PATH)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        // Resize gambar ke ukuran yang dibutuhkan model
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true)

        // Alokasi ByteBuffer
        val modelInputSize =
                4 * IMAGE_HEIGHT * IMAGE_WIDTH * 1 // 4 byte per float, 1 channel (grayscale)
        val imgData = ByteBuffer.allocateDirect(modelInputSize)
        imgData.order(ByteOrder.nativeOrder())  

        // Konversi gambar ke grayscale dan normalisasi (0-1)
        for (y in 0 until IMAGE_HEIGHT) {
            for (x in 0 until IMAGE_WIDTH) {
                val pixel = resizedBitmap.getPixel(x, y)

                // Konversi RGB ke grayscale
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val gray = (0.299f * r + 0.587f * g + 0.114f * b) / 255.0f

                imgData.putFloat(gray)
            }
        }

        imgData.rewind()
        return imgData
    }

    fun recognize(bitmap: Bitmap): Pair<String, Float> {
        if (interpreter == null) {
            return Pair("Model tidak dimuat", 0.0f)
        }

        try {
            // Preprocess gambar
            val imgData = preprocessImage(bitmap)

            // Persiapkan output
            val outputBuffer = Array(1) { FloatArray(NUM_CLASSES) }

            // Inferensi
            interpreter?.run(imgData, outputBuffer)

            // Temukan kelas dengan probabilitas tertinggi
            val result = outputBuffer[0]
            var maxIndex = 0
            var maxValue = result[0]

            for (i in 1 until NUM_CLASSES) {
                if (result[i] > maxValue) {
                    maxValue = result[i]
                    maxIndex = i
                }
            }

            // Kembalikan label dan confidence
            return Pair(labels[maxIndex], maxValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error during recognition: ${e.message}")
            return Pair("Error: ${e.message}", 0.0f)
        }
    }

    fun close() {
        interpreter?.close()
    }
}
