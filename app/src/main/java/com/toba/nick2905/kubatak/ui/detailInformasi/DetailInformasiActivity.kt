package com.toba.nick2905.kubatak.ui.detailInformasi

import androidx.recyclerview.widget.LinearLayoutManager
import com.toba.nick2905.kubatak.R
import com.toba.nick2905.kubatak.base.BaseActivityBinding
import com.toba.nick2905.kubatak.data.local.Kebudayaan
import com.toba.nick2905.kubatak.data.local.Kesusastraan
import com.toba.nick2905.kubatak.databinding.ActivityDetailInformasiBinding

class DetailInformasiActivity : BaseActivityBinding<ActivityDetailInformasiBinding>() {
    private lateinit var adapterKebudayaanAdapter: KebudayaanAdapter
    private var listBudaya = ArrayList<Kebudayaan>()

    private lateinit var adapterKesusastraanAdapter: KesusastraanAdapter
    private var listSastra = ArrayList<Kesusastraan>()
    override fun bindingInflater(): ActivityDetailInformasiBinding {
        return ActivityDetailInformasiBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        with(binding) {
            rvKebudayaanInformasi.setHasFixedSize(true)
            rvKesusastraan.setHasFixedSize(true)
            backImageInformasi.setOnClickListener {
                onBackPressed()
            }
        }

        listBudaya.addAll(getListBudaya())
        listSastra.addAll(getListSastra())

        showRecyclerKebudayaan()
        showRecyclerKesusastraan()


    }

    private fun getListBudaya(): ArrayList<Kebudayaan> {
        val dataNameKebudayaan = resources.getStringArray(R.array.name_informasi_kebudayaan)
        val dataDescKebudayaan = resources.getStringArray(R.array.desc_informasi_kebudayaan)

        val listKebudayaan = ArrayList<Kebudayaan>()

        for (position in dataDescKebudayaan.indices) {
            val budaya = Kebudayaan(
                dataNameKebudayaan[position],
                dataDescKebudayaan[position]
            )
            listKebudayaan.add(budaya)
        }
        return listKebudayaan
    }

    private fun getListSastra(): ArrayList<Kesusastraan> {
        val dataNameKesusastraan = resources.getStringArray(R.array.name_informasi_kesusastraan)
        val dataDescKesusastraan = resources.getStringArray(R.array.desc_informasi_kesusastraan)

        val listSastraAll = ArrayList<Kesusastraan>()

        for (position in dataDescKesusastraan.indices) {
            val sastra = Kesusastraan(
                dataNameKesusastraan[position],
                dataDescKesusastraan[position]
            )
            listSastraAll.add(sastra)
        }
        return listSastraAll
    }

    private fun showRecyclerKebudayaan() {
        with(binding) {
            rvKebudayaanInformasi.layoutManager =
                LinearLayoutManager(this.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapterKebudayaanAdapter = KebudayaanAdapter(listBudaya)
            rvKebudayaanInformasi.adapter = adapterKebudayaanAdapter
        }
    }

    private fun showRecyclerKesusastraan() {
        with(binding) {
            rvKesusastraan.layoutManager =
                LinearLayoutManager(this.root.context, LinearLayoutManager.HORIZONTAL, false)
            adapterKesusastraanAdapter = KesusastraanAdapter(listSastra)
            rvKesusastraan.adapter = adapterKesusastraanAdapter
        }
    }
}
