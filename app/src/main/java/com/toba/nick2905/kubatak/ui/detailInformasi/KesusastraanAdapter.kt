package com.toba.nick2905.kubatak.ui.detailInformasi

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Kesusastraan
import com.toba.nick2905.kubatak.databinding.ItemDetailBatakBinding
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKesusastraan.AnakSurat.DetailAnakSurat
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKesusastraan.InaSurat.DetailInaSurat
import com.toba.nick2905.kubatak.ui.detailInformasi.detailKesusastraan.SuratBatak.DetailSuratBatak

class KesusastraanAdapter(private val listKesusastraan: ArrayList<Kesusastraan>) :
    RecyclerView.Adapter<KesusastraanAdapter.KesusastraanViewHolder>() {
    inner class KesusastraanViewHolder(private val binding: ItemDetailBatakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sastra: Kesusastraan) {
            with(binding) {
                nameBatakDetail.text = sastra.judul
                descBatakDetail.text = sastra.desc
                itemView.setOnClickListener {
                    when (layoutPosition) {
                        0 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailSuratBatak::class.java
                            )
                        )
                        1 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailInaSurat::class.java
                            )
                        )
                        2 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailAnakSurat::class.java
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KesusastraanViewHolder {
        return KesusastraanViewHolder(
            ItemDetailBatakBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listKesusastraan.size

    override fun onBindViewHolder(holder: KesusastraanViewHolder, position: Int) {
        holder.bind(listKesusastraan[position])
    }
}