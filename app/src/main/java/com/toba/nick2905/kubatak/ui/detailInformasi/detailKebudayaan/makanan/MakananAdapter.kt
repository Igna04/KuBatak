package com.toba.nick2905.kubatak.ui.detailInformasi.detailKebudayaan.makanan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toba.nick2905.kubatak.data.local.Makanan
import com.toba.nick2905.kubatak.databinding.ItemKulinerBinding

class MakananAdapter(private val listMakanan: List<Makanan>) :
    RecyclerView.Adapter<MakananAdapter.MakananViewHolder>() {

    inner class MakananViewHolder(private val binding: ItemKulinerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Makanan) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.imgMakanan)
                    .into(imgKuliner)
                namaKuliner.text = item.nameMakanan
                aksaraKuliner.text = item.aksaraMakanan
                descIndoKuliner.text = item.descMakanan
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        return MakananViewHolder(
            ItemKulinerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listMakanan.size

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        holder.bind(listMakanan[position])
    }

}