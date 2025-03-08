package com.toba.nick2905.kubatak.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Mataangin
import com.toba.nick2905.kubatak.databinding.ItemMainCompassBinding

class MataAdapter(private val listMata: ArrayList<Mataangin>) :
    RecyclerView.Adapter<MataAdapter.MataViewHolder>() {

    inner class MataViewHolder(private val binding: ItemMainCompassBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mata: Mataangin) {
            with(binding) {
                nameBatakMata.text = mata.namaBatakMataangin
                nameBahasaMata.text = mata.namaBahasaMataangin
                nameAksaraMata.text = mata.namaAksaraMataangin
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MataViewHolder {
        return MataViewHolder(
            ItemMainCompassBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listMata.size

    override fun onBindViewHolder(holder: MataViewHolder, position: Int) {
        holder.bind(listMata[position])
    }

}