package com.raydev.quran.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.raydev.quran.R
import com.raydev.quran.databinding.ItemAyatBinding
import com.raydev.shared.model.Ayat

class AyatAdapter(
    private val context: Context
): RecyclerView.Adapter<AyatAdapter.ViewHolder>() {

    private val ayatList = ArrayList<Ayat>()

    fun setAyatList(ayatList: List<Ayat>){
        this.ayatList.clear()
        this.ayatList.addAll(ayatList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatAdapter.ViewHolder {
        val itemBinding = ItemAyatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AyatAdapter.ViewHolder, position: Int) {
        val ayat = ayatList[position]
        holder.bind(ayat)
    }

    override fun getItemCount(): Int = ayatList.size

    inner class ViewHolder(
        private val itemBinding: ItemAyatBinding
    ): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(ayat: Ayat){
            itemBinding.tvNumber.text = ayat.nomor
            itemBinding.tvAyat.text = ayat.arab
            itemBinding.tvArti.text = ayat.indo
            if(adapterPosition % 2 == 0 )
                itemBinding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_ayat))
            else
                itemBinding.container.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }


}