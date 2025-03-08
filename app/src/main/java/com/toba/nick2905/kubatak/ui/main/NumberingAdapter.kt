package com.toba.nick2905.kubatak.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Numbering
import com.toba.nick2905.kubatak.databinding.ItemMainNumberingBinding
import com.toba.nick2905.kubatak.ui.detailPenomoran.angka.DetailAngka
import com.toba.nick2905.kubatak.ui.detailPenomoran.bulan.DetailBulan
import com.toba.nick2905.kubatak.ui.detailPenomoran.hari.DetailHari
import com.toba.nick2905.kubatak.ui.detailPenomoran.jam.DetailJam

class NumberingAdapter(private val listNumber: ArrayList<Numbering>) :
    RecyclerView.Adapter<NumberingAdapter.NumberingViewHolder>() {

    inner class NumberingViewHolder(private val binding: ItemMainNumberingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(number: Numbering) {
            with(binding) {
                nameNumbering.text = number.name
                descNumbering.text = number.desc
                itemView.setOnClickListener {
                    when (layoutPosition) {
                        0 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailAngka::class.java
                            )
                        )
                        1 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailJam::class.java
                            )
                        )
                        2 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailHari::class.java
                            )
                        )
                        3 -> itemView.context.startActivity(
                            Intent(
                                itemView.context,
                                DetailBulan::class.java
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberingViewHolder {
        return NumberingViewHolder(
            ItemMainNumberingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listNumber.size

    override fun onBindViewHolder(holder: NumberingViewHolder, position: Int) {
        holder.bind(listNumber[position])
    }

}