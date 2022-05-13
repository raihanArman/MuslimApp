package com.raydev.quran.ui.list_surah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raydev.quran.databinding.ItemSurahBinding
import com.raydev.shared.model.Surah

class SurahAdapter(
    val goToDetail: (String) -> Unit,
//    val downloadSurah: (Surah) -> Unit
): RecyclerView.Adapter<SurahAdapter.ViewHolder>() {

    private val surahList = ArrayList<Surah>()

    fun setSurahList(surahList: List<Surah>){
        this.surahList.clear()
        this.surahList.addAll(surahList)
        notifyDataSetChanged()
    }

    fun getSurahList(): ArrayList<Surah>{
        return surahList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemSurahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surah = surahList[position]
        holder.bind(surah)
    }

    override fun getItemCount(): Int = surahList.size

    inner class ViewHolder(
        private val itemBinding: ItemSurahBinding
    ): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(surah: Surah){
            itemBinding.tvSurahName.text = surah.nama
            (surah.type + " - " + surah.ayat + " Ayat ").also { itemBinding.tvSumAyat.text = it }
            itemView.setOnClickListener {
                goToDetail(surah.nomor)
            }
        }
    }

}