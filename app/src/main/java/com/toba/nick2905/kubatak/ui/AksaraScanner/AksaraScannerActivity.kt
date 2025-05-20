package com.toba.nick2905.kubatak.ui.AksaraScanner

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.toba.nick2905.kubatak.R
import com.toba.nick2905.kubatak.databinding.ActivityAksaraScannerBinding
import com.toba.nick2905.kubatak.helper.AksaraRecognitionHelper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AksaraScannerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAksaraScannerBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private lateinit var aksaraRecognitionHelper: AksaraRecognitionHelper

    private val TAG = "AksaraScannerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAksaraScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi helper untuk pengenalan aksara
        aksaraRecognitionHelper = AksaraRecognitionHelper(this)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the capture button listener
        binding.captureButton.setOnClickListener { captureImage() }

        // Back button
        binding.btnBack.setOnClickListener { finish() }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun captureImage() {
        val bitmap = binding.viewFinder.bitmap
        if (bitmap != null) {
            // analyzeImage(bitmap)
        } else {
            Toast.makeText(this, "Tidak dapat mengambil gambar", Toast.LENGTH_SHORT).show()
        }
    }

    // private fun analyzeImage(bitmap: Bitmap) {
    //     // Tampilkan preview gambar yang diambil
    //     binding.imagePreview.setImageBitmap(bitmap)

    //     // Lakukan pengenalan aksara dan terjemahan aksara
    //     val result = aksaraRecognitionHelper.recognizeAndTranslate(bitmap)

    //     // Tampilkan hasil
    //     binding.tvResult.text = "Aksara: ${result.originalAksara}"
    //     binding.tvConfidence.text = "Tingkat keyakinan: ${(result.confidence * 100).toInt()}%"

    //     // Tambahan tampilan untuk hasil terjemahan
    //     binding.tvBatakWord.text = "Kata Batak : ${result.batakWord}"
    //     binding.tvIndonesianMeaning.text = "Arti dalam bahasa Indonesai : ${result.indonesianMeaning}"
    // }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(
                {
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                    // Preview
                    val preview =
                            Preview.Builder().build().also {
                                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                            }

                    // Image capture
                    imageCapture =
                            ImageCapture.Builder()
                                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                    .build()

                    // Select back camera by default
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        // Bind use cases to camera
                        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                    } catch (e: Exception) {
                        Log.e(TAG, "Use case binding failed", e)
                    }
                },
                ContextCompat.getMainExecutor(this)
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Izin kamera diperlukan untuk fitur ini", Toast.LENGTH_SHORT)
                        .show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        aksaraRecognitionHelper.close()
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
