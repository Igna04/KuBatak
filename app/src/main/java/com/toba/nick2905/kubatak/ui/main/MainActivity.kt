package com.toba.nick2905.kubatak.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.toba.nick2905.kubatak.R
import com.toba.nick2905.kubatak.base.BaseActivityBinding
import com.toba.nick2905.kubatak.data.local.Mataangin
import com.toba.nick2905.kubatak.data.local.Numbering
import com.toba.nick2905.kubatak.databinding.ActivityMainBinding
import com.toba.nick2905.kubatak.ui.detailInformasi.DetailInformasiActivity
import com.toba.nick2905.kubatak.ui.kamus.KamusActivity

class MainActivity : BaseActivityBinding<ActivityMainBinding>() {
    private lateinit var adapterNumbering: NumberingAdapter
    private var listNumber = ArrayList<Numbering>()

    private lateinit var adapterMata: MataAdapter
    private var listMata = ArrayList<Mataangin>()

    private lateinit var bottomNavigation: BottomNavigationView

    override fun bindingInflater(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupView() {

        with(binding) {
            rvNumberingMain.setHasFixedSize(true)
            rvMataAnginMain.setHasFixedSize(true)
            lnrInformasiBatak.setOnClickListener {
                startActivity(Intent(this.root.context, DetailInformasiActivity::class.java))
            }

            lnrKamusBatakIndo.setOnClickListener {
                startActivity(Intent(this.root.context, KamusActivity::class.java))
            }
        }

        listNumber.addAll(getListNumbering())
        listMata.addAll(getListMata())

        showRecyclerNumbering()
        showRecyclerMata()

        bottomNavigation = binding.bottomNavigation
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_camera -> {
                    if (ContextCompat.checkSelfPermission(
                                    this,
                                    android.Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        openCamera()
                    } else {
                        ActivityCompat.requestPermissions(
                                this,
                                arrayOf(android.Manifest.permission.CAMERA),
                                CAMERA_PERMISSION_CODE
                        )
                    }
                }
            }
            true
        }
    }

    private fun openCamera() {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Tidak dapat membuka kamera", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                Toast.makeText(this, "Izin kamera dibutuhkan untuk fitur ini", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun showRecyclerNumbering() {
        with(binding) {
            rvNumberingMain.layoutManager =
                    LinearLayoutManager(this.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapterNumbering = NumberingAdapter(listNumber)
            rvNumberingMain.adapter = adapterNumbering
        }
    }

    private fun showRecyclerMata() {
        with(binding) {
            rvMataAnginMain.layoutManager =
                    LinearLayoutManager(this.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapterMata = MataAdapter(listMata)
            rvMataAnginMain.adapter = adapterMata
        }
    }

    private fun getListNumbering(): ArrayList<Numbering> {
        val dataNameNumbering = resources.getStringArray(R.array.name_penomoran)
        val dataDescNumbering = resources.getStringArray(R.array.deskripsi_penomoran)

        val listNumber = ArrayList<Numbering>()

        for (position in dataNameNumbering.indices) {
            val number = Numbering(dataNameNumbering[position], dataDescNumbering[position])
            listNumber.add(number)
        }
        return listNumber
    }

    private fun getListMata(): ArrayList<Mataangin> {
        val dataBatakMata = resources.getStringArray(R.array.name_mata_angin_batak)
        val dataBahasaMata = resources.getStringArray(R.array.name_mata_angin)
        val dataAksaraMata = resources.getStringArray(R.array.name_mata_angin_aksara)

        val listMata = ArrayList<Mataangin>()

        for (position in dataBatakMata.indices) {
            val mata =
                    Mataangin(
                            dataBahasaMata[position],
                            dataAksaraMata[position],
                            dataBatakMata[position]
                    )
            listMata.add(mata)
        }
        return listMata
    }
}
