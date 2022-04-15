package com.raydev.quran.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.raydev.anabstract.base.BaseActivity
import com.raydev.anabstract.state.ResponseState
import com.raydev.quran.R
import com.raydev.quran.databinding.ActivityDetailSurahBinding
import com.raydev.quran.di.QuranModule
import com.raydev.quran.di.QuranModule.quranModule
import com.raydev.quran.viewmodel.AyatViewModel
import com.raydev.quran.viewmodel.SurahViewModel
import com.raydev.shared.model.Ayat
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailSurahActivity : BaseActivity<ActivityDetailSurahBinding>() {
    private val viewModel: AyatViewModel by viewModel()
    private val adapter: AyatAdapter by lazy {
        AyatAdapter(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(quranModule)

        setupAdapter()
        setupAyat()
        observeAyat()

    }

    private fun observeAyat() {
        viewModel.observableAyat.observe(this,{response ->
            when(response){
                is ResponseState.Success ->{
                    binding.progressBar.visibility = View.INVISIBLE
                    setupDataAyat(response.data)
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

    private fun setupDataAyat(data: List<Ayat>) {
        adapter.setAyatList(data)
    }

    private fun setupAyat() {
        val number = intent.getStringExtra("number")
        viewModel.loadAyat(number!!)
    }

    override fun getViewBinding() = ActivityDetailSurahBinding.inflate(layoutInflater)

    private fun setupAdapter(){
        binding.rvAyat.layoutManager = LinearLayoutManager(this)
        binding.rvAyat.adapter = adapter
    }
}