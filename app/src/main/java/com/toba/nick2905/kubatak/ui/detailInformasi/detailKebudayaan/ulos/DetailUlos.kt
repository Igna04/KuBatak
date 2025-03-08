package com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.ulos

import androidx.recyclerview.widget.LinearLayoutManager
import com.toba.nick2905.kubatak.R
import com.toba.nick2905.kubatak.base.BaseActivityBinding
import com.toba.nick2905.kubatak.data.local.Ulos
import com.toba.nick2905.kubatak.databinding.ActivityDetailUlosBinding

class DetailUlos : BaseActivityBinding<ActivityDetailUlosBinding>() {
    private lateinit var adapterUlos: UlosAdapter
    private var listUlos = ArrayList<Ulos>()
    override fun bindingInflater(): ActivityDetailUlosBinding {
        return ActivityDetailUlosBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        with(binding) {
            rvUlos.setHasFixedSize(true)
        }
        listUlos.addAll(getListUlos())
        showRecyclerUlos()
    }

    private fun getListUlos(): ArrayList<Ulos> {
        val dataNamaUlos = resources.getStringArray(R.array.name_ulos_batak)
        val dataAksaraUlos = resources.getStringArray(R.array.name_ulos_aksara)
        val dataBatakArti = resources.getStringArray(R.array.makna_ulos_batak)
        val dataIndonesiaArti = resources.getStringArray(R.array.makna_ulos_indonesia)

        val listAllUlos = ArrayList<Ulos>()
        for (position in dataNamaUlos.indices) {
            val ulos = Ulos(
                dataNamaUlos[position],
                dataAksaraUlos[position],
                dataBatakArti[position],
                dataIndonesiaArti[position]
            )
            listAllUlos.add(ulos)
        }
        return listAllUlos
    }

    private fun showRecyclerUlos() {
        with(binding) {
            rvUlos.layoutManager =
                LinearLayoutManager(this.root.context, LinearLayoutManager.VERTICAL, false)
            adapterUlos = UlosAdapter(listUlos)
            rvUlos.adapter = adapterUlos
        }
    }
}
