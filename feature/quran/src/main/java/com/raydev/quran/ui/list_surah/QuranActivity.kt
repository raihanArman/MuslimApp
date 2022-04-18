package com.raydev.quran.ui.list_surah

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.deeplinkdispatch.DeepLink
import com.raydev.anabstract.base.BaseActivity
import com.raydev.anabstract.state.ResponseState
import com.raydev.quran.databinding.ActivityQuranBinding
import com.raydev.quran.di.QuranModule.quranModule
import com.raydev.quran.ui.list_ayat.DetailSurahActivity
import com.raydev.quran.viewmodel.SurahViewModel
import com.raydev.shared.deeplink.AppLink
import com.raydev.shared.model.Surah
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


@DeepLink(AppLink.QuranFeature.QURAN_LINK)
class QuranActivity : BaseActivity<ActivityQuranBinding>() {
    private val viewModel: SurahViewModel by viewModel()
    private val adapter: SurahAdapter by lazy {
        SurahAdapter{
            goToDetail(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(quranModule)
        setupAdapter()
        setupSurah()
        observeSurah()

    }

    override fun getViewBinding() = ActivityQuranBinding.inflate(layoutInflater)

    private fun setupAdapter(){
        binding.rvSurah.layoutManager = LinearLayoutManager(this)
        binding.rvSurah.adapter = adapter
    }

    private fun setupSurah() {
        viewModel.loadSurah()
    }

    private fun setupDataSurah(surahList: List<Surah>){
        adapter.setSurahList(surahList)
    }

    private fun observeSurah() {
        viewModel.observableSurah.observe(this, {response ->
            when(response){
                is ResponseState.Success ->{
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data?.let { setupDataSurah(it) }
                }
                is ResponseState.Error ->{
                    binding.progressBar.visibility = View.INVISIBLE
                    onMessage(response.errorMessage)
                }
                is ResponseState.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }
    private fun goToDetail(number: String){
        val intent = Intent(this, DetailSurahActivity::class.java)
        intent.putExtra("number",number.toInt())
        intent.putParcelableArrayListExtra("surah_list", adapter.getSurahList())
        startActivity(intent)
    }

}