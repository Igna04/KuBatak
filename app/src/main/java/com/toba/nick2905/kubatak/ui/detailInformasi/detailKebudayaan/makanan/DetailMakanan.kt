package com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.makanan

import androidx.recyclerview.widget.LinearLayoutManager
import com.toba.nick2905.kubatak.R
import com.toba.nick2905.kubatak.base.BaseActivityBinding
import com.toba.nick2905.kubatak.data.local.Makanan
import com.toba.nick2905.kubatak.databinding.ActivityDetailMakananBinding

class DetailMakanan : BaseActivityBinding<ActivityDetailMakananBinding>() {
    private val listMakanan: MutableList<Makanan> = mutableListOf()
    private lateinit var adapterMakanan: MakananAdapter
    override fun bindingInflater(): ActivityDetailMakananBinding {
        return ActivityDetailMakananBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        with(binding) {
            rvMakanan.setHasFixedSize(true)
            listMakanan.addAll(getListMakanan())
            showRecyclerMakanan()
        }
    }


    private fun getListMakanan(): ArrayList<Makanan> {
        val dataNameMakanan = resources.getStringArray(R.array.name_makanan_batak)
        val dataAksaraMakanan = resources.getStringArray(R.array.name_makanan_aksara)
        val dataDescMakanan = resources.getStringArray(R.array.desc_makanan_batak)
        val dataImage = resources.obtainTypedArray(R.array.img_makanan_batak)

        val listMakananAll = ArrayList<Makanan>()
        listMakanan.clear()
        for (i in 0 until dataImage.length()) {
            val makanan = Makanan(
                dataImage.getResourceId(i, 0),
                dataNameMakanan[i],
                dataAksaraMakanan[i],
                dataDescMakanan[i]
            )

            listMakananAll.add(makanan)
        }
        dataImage.recycle()
        return listMakananAll
    }

    private fun showRecyclerMakanan() {
        with(binding) {
            rvMakanan.layoutManager =
                LinearLayoutManager(this.root.context, LinearLayoutManager.VERTICAL, false)
            adapterMakanan = MakananAdapter(listMakanan)
            rvMakanan.adapter = adapterMakanan
        }
    }
}
