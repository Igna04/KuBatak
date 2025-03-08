package com.toba.nick2905.kubatak.ui.kamus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toba.nick2905.kubatak.data.local.Kamus
import com.toba.nick2905.kubatak.databinding.ItemKamusBinding
import java.util.*

class KamusAdapter(private val modellist: ArrayList<Kamus>) :
    RecyclerView.Adapter<KamusAdapter.KamusViewHolder>() {
    val TYPE = 1
    var tempModelList = ArrayList<Kamus>()

    inner class KamusViewHolder(private val binding: ItemKamusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(kamus: Kamus) {
            with(binding) {
                kamusBatak.text = kamus.kamusBatak
                artiKamusBatak.text = kamus.artiIndonesia
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KamusViewHolder {
        return when (viewType) {
            TYPE -> {
                return KamusViewHolder(
                    ItemKamusBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                KamusViewHolder(
                    ItemKamusBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = modellist.size

    override fun onBindViewHolder(holder: KamusViewHolder, position: Int) {
        holder.bind(modellist[position])
    }


    fun filter(charText: String) {
        var tempCharText = charText
        tempCharText = tempCharText.lowercase(Locale.getDefault())
        if (tempModelList.size < 1) {
            tempModelList.addAll(modellist)
        }
        modellist.clear()
        if (tempCharText.isEmpty()) {
            modellist.addAll(tempModelList)
        } else {
            for (model in tempModelList) {
                if (model.kamusBatak.lowercase(Locale.getDefault())
                        .contains(tempCharText)
                ) {
                    modellist.add(model)
                } else if (model.artiIndonesia.lowercase(Locale.getDefault())
                        .contains(tempCharText)
                ) {
                    modellist.add(model)
                }
            }
        }
        notifyDataSetChanged()
    }
}