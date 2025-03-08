package com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.ulos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Ulos
import com.toba.nick2905.kubatak.databinding.ItemDetailHariBinding

class UlosAdapter(private val listUlos: ArrayList<Ulos>) :
    RecyclerView.Adapter<UlosAdapter.UlosViewHolder>() {
    inner class UlosViewHolder(private val binding: ItemDetailHariBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ulos: Ulos) {
            with(binding) {
                txtNamaHariBatak.text = ulos.ulosBatak
                txtAksaraHariBatak.text = ulos.ulosAksara
                txtArtiBatak.text = ulos.maknaBatak
                txtArtiIndonesia.text = ulos.maknaIndonesia
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UlosViewHolder {
        return UlosViewHolder(
            ItemDetailHariBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listUlos.size

    override fun onBindViewHolder(holder: UlosViewHolder, position: Int) {
        holder.bind(listUlos[position])
    }
}