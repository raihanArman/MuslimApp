package com.raydev.quran

import android.os.Bundle
import com.raydev.anabstract.base.BaseActivity
import com.raydev.anabstract.state.ResponseState
import com.raydev.quran.databinding.ActivityQuranBinding
import com.raydev.quran.viewmodel.SurahViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class QuranActivity : BaseActivity<ActivityQuranBinding>() {
    private val viewModel: SurahViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSurah()
        observeSurah()

    }

    override fun getViewBinding() = ActivityQuranBinding.inflate(layoutInflater)

    private fun setupSurah() {
        viewModel.loadSurah()
    }

    private fun observeSurah() {
        viewModel.observableSurah.observe(this, {response ->
            when(response){
                is ResponseState.Success ->{

                }
                is ResponseState.Error ->{
                    onMessage(response.errorMessage)
                }
                is ResponseState.Loading ->{

                }
            }
        })
    }
}