package com.raydev.quran.ui.list_ayat

import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.raydev.shared.model.Surah

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val surahTabsList: ArrayList<Surah> = ArrayList()

    fun setSurahTabsList(surahTabsList: List<Surah>){
        this.surahTabsList.clear()
        this.surahTabsList.addAll(surahTabsList)
    }

    override fun createFragment(position: Int): Fragment {
        return AyatBySurahFragment()
    }

    override fun getItemCount(): Int = surahTabsList.size

}