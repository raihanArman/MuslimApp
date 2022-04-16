package com.raydev.quran.ui.list_ayat

import android.os.Bundle
import com.raydev.anabstract.base.BaseActivity
import com.raydev.quran.databinding.ActivityDetailSurahBinding
import com.raydev.quran.di.QuranModule.quranModule
import org.koin.core.context.loadKoinModules

import com.google.android.material.tabs.TabLayoutMediator
import com.raydev.shared.model.Surah


class DetailSurahActivity : BaseActivity<ActivityDetailSurahBinding>() {
//    private val viewModel: AyatViewModel by viewModel()
//    private val adapter: AyatAdapter by lazy {
//        AyatAdapter(this)
//    }
    var surahList = ArrayList<Surah>()
    var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(quranModule)

        surahList = intent.getParcelableArrayListExtra<Surah>("surah_list") as ArrayList<Surah>
        number = intent.getIntExtra("number", 0)

//        setupAdapter()
//        setupAyat()
//        observeAyat()
        val adapter = ViewPagerAdapter(this)
        adapter.setSurahTabsList(surahList)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(
            binding.tabs, binding.viewpager
        ) { tab, position -> tab.text = "${surahList[position].nama}" }.attach()

        setCurrentSurah()

    }

    private fun setCurrentSurah(){
        if(number != 0)
            binding.viewpager.currentItem = number-1
        else
            binding.viewpager.currentItem = number
    }

//    private fun observeAyat() {
//        viewModel.observableAyat.observe(this,{response ->
//            when(response){
//                is ResponseState.Success ->{
//                    binding.progressBar.visibility = View.INVISIBLE
//                    setupDataAyat(response.data)
//                }
//                is ResponseState.Error ->{
//                    binding.progressBar.visibility = View.INVISIBLE
//                    onMessage(response.errorMessage)
//                }
//                is ResponseState.Loading ->{
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//            }
//        })
//    }
//
//    private fun setupDataAyat(data: List<Ayat>) {
//        adapter.setAyatList(data)
//    }
//
//    private fun setupAyat() {
//        val number = intent.getStringExtra("number")
//        viewModel.loadAyat(number!!)
//    }
//
    override fun getViewBinding() = ActivityDetailSurahBinding.inflate(layoutInflater)
//
//    private fun setupAdapter(){
//        binding.rvAyat.layoutManager = LinearLayoutManager(this)
//        binding.rvAyat.adapter = adapter
//    }
}