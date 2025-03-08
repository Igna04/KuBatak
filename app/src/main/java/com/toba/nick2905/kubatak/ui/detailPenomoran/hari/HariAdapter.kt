package com.toba.nick2905.kubatak.ui.detailPenomoran.hari

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Hari
import com.toba.nick2905.kubatak.databinding.ItemDetailHariBinding

class HariAdapter(private val listHari: ArrayList<Hari>) :
    RecyclerView.Adapter<HariAdapter.HariViewHolder>() {

    inner class HariViewHolder(private val binding: ItemDetailHariBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hari: Hari) {
            with(binding) {
                txtNamaHariBatak.text = hari.batakHari
                txtAksaraHariBatak.text = hari.aksaraHari
                txtArtiBatak.text = hari.batakArtiHari
                txtArtiIndonesia.text = hari.indonesiaArtiHari
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HariViewHolder {
        return HariViewHolder(
            ItemDetailHariBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listHari.size

    override fun onBindViewHolder(holder: HariViewHolder, position: Int) {
        holder.bind(listHari[position])
    }
}