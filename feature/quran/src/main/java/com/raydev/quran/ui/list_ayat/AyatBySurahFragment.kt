package com.raydev.quran.ui.list_ayat

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.raydev.anabstract.base.BaseFragment
import com.raydev.anabstract.state.ResponseState
import com.raydev.quran.databinding.FragmentAyatBySurahBinding
import com.raydev.quran.viewmodel.AyatViewModel
import com.raydev.shared.model.Ayat

class AyatBySurahFragment : BaseFragment<FragmentAyatBySurahBinding>() {

    private lateinit var viewModel: AyatViewModel

    private val adapter: AyatAdapter by lazy {
        AyatAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as DetailSurahActivity).viewModel

        setupAdapter()
//        setupAyat()
        observeAyat()
    }

    override fun getViewBinding() = FragmentAyatBySurahBinding.inflate(layoutInflater)

    private fun observeAyat() {
        viewModel.observableAyat.observe(requireActivity(),{response ->
            when(response){
                is ResponseState.Success ->{
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data?.let { setupDataAyat(it) }
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

//    private fun setupAyat() {
//        viewModel.observableCurrentSurah.observe(requireActivity(), {
//            viewModel.loadAyat(it.toString())
//        })
//    }

    private fun setupAdapter(){
        binding.rvAyat.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvAyat.adapter = adapter
    }

}