package com.raydev.prayer

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import com.airbnb.deeplinkdispatch.DeepLink
import com.raydev.anabstract.base.BaseActivity
import com.raydev.anabstract.extention.toDateFormatApiParameter
import com.raydev.anabstract.extention.toDateFormatDaysFullname
import com.raydev.anabstract.state.ResponseState
import com.raydev.prayer.databinding.ActivityPrayerBinding
import com.raydev.prayer.di.PrayerModule.prayerModule
import com.raydev.shared.deeplink.AppLink
import com.raydev.shared.model.SholatTime
import com.raydev.shared.util.Util
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import java.util.*


@DeepLink(AppLink.PrayerFeature.PRAYER_LINK)
class PrayerActivity : BaseActivity<ActivityPrayerBinding>() {
    private val TAG = "PrayerActivity"
    private val viewModel: PrayerViewModel by viewModel()
    var cityId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        loadKoinModules(prayerModule)

        setupCurrentLocationAndDate()
        setupObserveSearchCity()
        setupObserveGetSholatTime()
        searchCity()
    }

    private fun getSholatTime() {
        if(cityId.isNotEmpty()){
            val date = Calendar.getInstance().time.toDateFormatApiParameter()
            viewModel.getSholatTime(cityId, date)
        }
    }

    private fun setupObserveGetSholatTime() {
        viewModel.observableGetSholatTime.observe(this,{response ->
            when(response){
                is ResponseState.Success ->{
                    setSholatTime(response.data)
                }
                is ResponseState.Error ->{
                    onMessage(response.errorMessage)
                }
                is ResponseState.Loading ->{

                }
            }
        })
    }

    private fun setSholatTime(data: SholatTime?) {
        binding.tvTimeImsak.text = data?.schedule?.let {
            it.imsak
        }

        binding.tvTimeSubuh.text = data?.schedule?.let {
            it.subuh
        }

        binding.tvTimeDhuhur.text = data?.schedule?.let {
            it.dzuhur
        }

        binding.tvTimeAshar.text = data?.schedule?.let {
            it.ashar
        }

        binding.tvTimeMagrib.text = data?.schedule?.let {
            it.maghrib
        }

        binding.tvTimeIsya.text = data?.schedule?.let {
            it.isya
        }
    }

    private fun searchCity() {
        val city = Util.currentLocation
            .replace("Kabupaten ", "")
            .replace("Kota ", "")
        viewModel.searchCity(city)
    }

    private fun setupObserveSearchCity() {
        viewModel.observableSearchCity.observe(this,{response ->
            when(response){
                is ResponseState.Success ->{
                    if(response.data != null && response.data!!.isNotEmpty()){
                        cityId = response.data!![0].id ?: ""
                        Log.d(TAG, "setupObserveSearchCity: $cityId")
                        getSholatTime()
                    }
                }
                is ResponseState.Error ->{
                    onMessage(response.errorMessage)
                }
                is ResponseState.Loading ->{

                }
            }
        })
    }

    private fun setupCurrentLocationAndDate() {
        binding.tvCurrentLocation.text = Util.currentLocation
        binding.tvDate.text = Calendar.getInstance().time.toDateFormatDaysFullname()
    }

    override fun getViewBinding(): ActivityPrayerBinding = ActivityPrayerBinding.inflate(layoutInflater)


}