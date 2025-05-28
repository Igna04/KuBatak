package com.toba.nick2905.kubatak.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import org.tensorflow.lite.Interpreter

class AksaraRecognitionHelper(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val TAG = "AksaraRecognitionHelper"
    private val IMAGE_HEIGHT = 64
    private val IMAGE_WIDTH = 128
    private val NUM_CLASSES = 46
    private val TIME_STEPS = 16
    private val BLANK_INDEX = 45
    private val MODEL_PATH = "model_weights.tflite"

    private val labels =
            arrayOf(
                    "a",
                    "be",
                    "bi",
                    "bo",
                    "bu",
                    "da",
                    "de",
                    "di",
                    "ga",
                    "go",
                    "gu",
                    "ha",
                    "hi",
                    "i",
                    "ja",
                    "jo",
                    "l",
                    "la",
                    "li",
                    "lo",
                    "lu",
                    "ma",
                    "mo",
                    "mu",
                    "na",
                    "n",
                    "nga",
                    "ngu",
                    "ng",
                    "nu",
                    "o",
                    "pa",
                    "p",
                    "pu",
                    "re",
                    "ri",
                    "ro",
                    "ru",
                    "sa",
                    "su",
                    "ta",
                    "to",
                    "tu",
                    "u"
            )

    init {
        Log.d(TAG, "📌 init AksaraRecognitionHelper DIMULAI")
        try {
            Log.d(TAG, "📦 Mencoba openFd untuk model: $MODEL_PATH")
            val fileDescriptor = context.assets.openFd(MODEL_PATH)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = fileDescriptor.startOffset
            val declaredLength = fileDescriptor.declaredLength
            Log.d(TAG, "📦 Model file berhasil dibuka, mapping buffer...")

            val modelBuffer =
                    fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            interpreter = Interpreter(modelBuffer)
            Log.d(TAG, "✅ Model TFLite berhasil dimuat ke Interpreter")
            Toast.makeText(context, "✅ Model berhasil dimuat", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "❌ Gagal load model TFLite: ${e.message}")
            Toast.makeText(context, "❌ Gagal muat model: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true)
        val imgData =
                ByteBuffer.allocateDirect(1 * IMAGE_HEIGHT * IMAGE_WIDTH * 4) // float = 4 byte
        imgData.order(ByteOrder.nativeOrder())

        for (y in 0 until IMAGE_HEIGHT) {
            for (x in 0 until IMAGE_WIDTH) {
                val pixel = resizedBitmap.getPixel(x, y)
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

    fun decodeCTC(output: Array<Array<FloatArray>>): Pair<String, Float> {
        val result = StringBuilder()
        var lastIndex = -1
        var confidenceSum = 0f
        var count = 0

        for (t in 0 until TIME_STEPS) {
            val probs = output[0][t]
            val maxIndex = probs.indices.maxByOrNull { probs[it] } ?: continue
            val confidence = probs[maxIndex]

            if (maxIndex != lastIndex && maxIndex != BLANK_INDEX) {
                result.append(labels[maxIndex])
                confidenceSum += confidence
                count++
            }
            lastIndex = maxIndex
        }

        val avgConfidence = if (count > 0) confidenceSum / count else 0f
        return Pair(result.toString(), avgConfidence)
    }

    fun recognizeCTC(bitmap: Bitmap): Pair<String, Float> {
        Log.d(TAG, "recognizeCTC() dipanggil")

        if (interpreter == null) {
            Log.e(TAG, "Interpreter null: model tidak dimuat")
            return Pair("Model tidak dimuat", 0.0f)
        }

        return try {
            val input = preprocessImage(bitmap)
            val output = Array(1) { Array(TIME_STEPS) { FloatArray(NUM_CLASSES) } }
            interpreter?.run(input, output)
            Log.d(TAG, "Model berhasil dijalankan")
            decodeCTC(output)
        } catch (e: Exception) {
            Log.e(TAG, "Error during CTC recognition: ${e.message}")
            Pair("Error", 0.0f)
        }
    }

    fun close() {
        interpreter?.close()
    }
}
