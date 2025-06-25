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
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.toba.nick2905.kubatak.databinding.ActivityAksaraScannerBinding
import com.toba.nick2905.kubatak.helper.AksaraRecognitionHelper
import com.toba.nick2905.kubatak.helper.AksaraTranslator
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

        // Inisialisasi helper pengenalan aksara
        aksaraRecognitionHelper = AksaraRecognitionHelper(this)

        // Request permission kamera
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Tombol ambil gambar
        binding.captureButton.setOnClickListener { captureImage() }

        // Tombol kembali
        binding.btnBack.setOnClickListener { finish() }

        // Tombol ambil ulang
        binding.btnRetake.setOnClickListener {
            binding.imagePreview.setImageBitmap(null)
            binding.imagePreview.visibility = android.view.View.GONE
            binding.resultContainer.visibility = android.view.View.GONE

            binding.viewFinder.visibility = android.view.View.VISIBLE
            binding.captureButton.visibility = android.view.View.VISIBLE
            binding.btnRetake.visibility = android.view.View.GONE
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun captureImage() {
        val bitmap = binding.viewFinder.bitmap
        if (bitmap != null) {
            Log.d(TAG, "captureImage: Bitmap berhasil diambil")
            analyzeImage(bitmap)
        } else {
            Log.e(TAG, "captureImage: Gagal mengambil bitmap")
            Toast.makeText(this, "Tidak dapat mengambil gambar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun analyzeImage(bitmap: Bitmap) {
        Log.d(TAG, "analyzeImage: Dipanggil")

        binding.imagePreview.setImageBitmap(bitmap)
        binding.imagePreview.visibility = android.view.View.VISIBLE
        binding.progressBar.visibility = android.view.View.VISIBLE

        binding.viewFinder.visibility = android.view.View.GONE
        binding.captureButton.visibility = android.view.View.GONE

        binding.cardResult.visibility = android.view.View.GONE
        binding.resultContainer.visibility = android.view.View.GONE

        binding.btnRetake.visibility = android.view.View.VISIBLE

        aksaraRecognitionHelper.recognizeCTC(bitmap) { aksara, confidence ->
            val translator = AksaraTranslator(this)
            val (batakWord, arti) = translator.translateAksara(aksara)

            runOnUiThread {
                Log.d(TAG, "analyzeImage: Hasil aksara = $aksara")
                Log.d(TAG, "analyzeImage: Hasil arti = $arti")

                binding.tvResult.text = "Aksara: $aksara"
                binding.tvConfidence.text = "Tingkat keyakinan: ${(confidence * 100).toInt()}%"
                binding.tvBatakWord.text = "Kata Batak : $batakWord"
                binding.tvIndonesianMeaning.text = "Arti dalam bahasa Indonesia : $arti"

                binding.progressBar.visibility = android.view.View.GONE
                binding.cardResult.visibility = android.view.View.VISIBLE
                binding.resultContainer.visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e(TAG, "startCamera: Gagal binding kamera", e)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(baseContext, it) ==
                PackageManager.PERMISSION_GRANTED
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Izin kamera diperlukan", Toast.LENGTH_SHORT).show()
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
