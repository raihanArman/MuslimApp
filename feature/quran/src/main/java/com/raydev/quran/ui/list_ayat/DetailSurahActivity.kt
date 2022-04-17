package com.raydev.quran.ui.list_ayat

import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.raydev.anabstract.base.BaseActivity
import com.raydev.quran.databinding.ActivityDetailSurahBinding
import com.raydev.quran.di.QuranModule.quranModule
import org.koin.core.context.loadKoinModules

import com.google.android.material.tabs.TabLayoutMediator
import com.raydev.quran.viewmodel.AyatViewModel
import com.raydev.shared.model.Surah
import org.koin.android.viewmodel.ext.android.viewModel


class DetailSurahActivity : BaseActivity<ActivityDetailSurahBinding>() {
    val viewModel: AyatViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(quranModule)

        getSurahListFromIntent()
        setupViewPager()
        setCurrentSurah()

    }

    private fun getSurahListFromIntent() {
        val surahList = intent.getParcelableArrayListExtra<Surah>("surah_list") as ArrayList<Surah>
        val number = intent.getIntExtra("number", 0)

        Log.d("DetailSurah", "getSurahListFromIntent: $number")
//        viewModel.setCurrentSurah(number)
        viewModel.setSurahList(surahList)
        if(number != 0)
            viewModel.setCurrentSurahPagerIndex(number - 1)
        else
            viewModel.setCurrentSurahPagerIndex(number)
    }

    private fun setupViewPager(){
        val adapter = ViewPagerAdapter(this)
        adapter.setSurahTabsList(viewModel.surahList)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(
            binding.tabs, binding.viewpager
        ) { tab, position -> tab.text = viewModel.surahList[position].nama }.attach()
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.loadAyat((position+1).toString())
            }
        })
    }

    private fun setCurrentSurah(){
        binding.viewpager.currentItem = viewModel.currentIndexSurahPager
    }


    override fun getViewBinding() = ActivityDetailSurahBinding.inflate(layoutInflater)

}