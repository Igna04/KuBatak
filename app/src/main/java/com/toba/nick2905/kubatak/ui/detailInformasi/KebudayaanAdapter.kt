package com.toba.nick2905.kubatak.ui.detailInformasi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Kebudayaan
import com.toba.nick2905.kubatak.databinding.ItemDetailBatakBinding
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.bendera.DetailBendera
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.kepercayaan.DetailKepercayaan
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.makanan.DetailMakanan
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.sejarah.DetailSejarah
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.ulos.DetailUlos

class KebudayaanAdapter(private val listKebudayaan: ArrayList<Kebudayaan>) :
    RecyclerView.Adapter<KebudayaanAdapter.KebudayaanViewHolder>() {
    inner class KebudayaanViewHolder(private val binding: ItemDetailBatakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(budaya: Kebudayaan) {
            with(binding) {
                nameBatakDetail.text = budaya.nameDetailBatak
                descBatakDetail.text = budaya.descDetailBatak
                itemView.setOnClickListener {
                    when (layoutPosition) {
                        0 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailSejarah::class.java
                            )
                        )
                        1 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailBendera::class.java
                            )
                        )
                        2 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailUlos::class.java
                            )
                        )
                        3 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailKepercayaan::class.java
                            )
                        )
                        4 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailMakanan::class.java
                            )
                        )
                        else -> {
                            Toast.makeText(
                                itemView.context,
                                "Sabar yah, kontennya belum ada.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KebudayaanViewHolder {
        return KebudayaanViewHolder(
            ItemDetailBatakBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listKebudayaan.size

    override fun onBindViewHolder(holder: KebudayaanViewHolder, position: Int) {
        holder.bind(listKebudayaan[position])
    }
}