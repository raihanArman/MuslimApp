package com.raydev.data.repository

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar
import com.google.android.gms.maps.model.LatLng
import com.raihanarman.location.LocationManager
import com.raihanarman.prayer.PrayTimeScript
import com.raihanarman.prayer.getCurrentPrayerTimeString
import com.raihanarman.prayer.getTimeUntilNextPrayerString
import com.raydev.anabstract.state.ResponseState
import com.raydev.data.datasource.pref.SharedPreferenceSource
import com.raydev.data.datasource.remote.PrayerRemoteDataSource
import com.raydev.domain.repository.PrayerRepository
import com.raydev.shared.model.City
import com.raydev.shared.model.NextPrayerTime
import com.raydev.shared.model.PrayerData
import com.raydev.shared.model.PrayerTime
import com.raydev.shared.model.SholatTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class PrayerRepositoryImpl(
    private val remoteDataSource: PrayerRemoteDataSource,
    private val sharedPreferenceSource: SharedPreferenceSource
) : PrayerRepository {

    private val locationManager: LocationManager = LocationManager.instance
    private val _flowPrayerTime: MutableStateFlow<PrayerTime> = MutableStateFlow(PrayerTime())

    override fun searchCity(city: String): Flow<ResponseState<List<City>>> {
        return remoteDataSource.searchCity(city)
    }

    override fun getSholatTime(cityId: String, date: String): Flow<ResponseState<SholatTime>> {
        return remoteDataSource.getSholatTime(cityId, date)
    }

    override fun getCurrentPrayerTime(): Flow<PrayerTime> = _flowPrayerTime

    override fun setImsakData(prayerData: PrayerData) {
        sharedPreferenceSource.setImsakData(prayerData)
    }

    override fun setSubuhData(prayerData: PrayerData) {
        sharedPreferenceSource.setSubuhData(prayerData)
    }

    override fun setDhuhurData(prayerData: PrayerData) {
        sharedPreferenceSource.setDhuhurData(prayerData)
    }

    override fun setAsharData(prayerData: PrayerData) {
        sharedPreferenceSource.setAsharData(prayerData)
    }

    override fun setMaghribData(prayerData: PrayerData) {
        sharedPreferenceSource.setMaghribData(prayerData)
    }

    override fun setIsyaData(prayerData: PrayerData) {
        sharedPreferenceSource.setIsyaData(prayerData)
    }

    override fun getImsakData(): PrayerData = sharedPreferenceSource.getImsakData()

    override fun getSubuhData(): PrayerData = sharedPreferenceSource.getSubuhData()

    override fun getDhuhurData(): PrayerData = sharedPreferenceSource.getDzuhurData()

    override fun getAsharData(): PrayerData = sharedPreferenceSource.getAsharData()

    override fun getMaghribData(): PrayerData = sharedPreferenceSource.getMaghribData()

    override fun getIsyaData(): PrayerData = sharedPreferenceSource.getIsyaData()
    override fun setCurrentPrayerTime(latLng: LatLng) {
        sharedPreferenceSource.userCoordinates = latLng

        val address = locationManager.getAddressLocation(latLng)
        address?.let {
            sharedPreferenceSource.userCity = it.locality
        }

        val prayers = PrayTimeScript()
        prayers.setTimeFormat(prayers.Time24)
        prayers.setCalcMethod(sharedPreferenceSource.calculationMethod)
        prayers.setAsrJuristic(sharedPreferenceSource.asrJuristic)
        prayers.setAdjustHighLats(sharedPreferenceSource.higherLatitudes)
        val offsets = intArrayOf(
            sharedPreferenceSource.fajrOffset,
            2,
            sharedPreferenceSource.dhuhrOffset,
            sharedPreferenceSource.asrOffset,
            2,
            sharedPreferenceSource.maghribOffset,
            sharedPreferenceSource.isyaOffset
        )
        prayers.tune(offsets)

        val mCalendar = GregorianCalendar()
        val mTimeZone = mCalendar.timeZone
        val mGMTOffset = mTimeZone.rawOffset
        val now = Date()
        val cal = Calendar.getInstance()
        cal.time = now
        val prayerTimes = prayers.getPrayerTimes(
            cal,
            latLng.latitude,
            latLng.longitude,
            TimeUnit.HOURS.convert(mGMTOffset.toLong(), TimeUnit.MILLISECONDS).toDouble()
        )

        val prayerTime = PrayerTime(
            sharedPreferenceSource.userCity,
            prayerTimes[0],
            prayerTimes[1],
            prayerTimes[2],
            prayerTimes[3],
            prayerTimes[4],
            prayerTimes[5],
            prayerTimes[6]
        )
        sharedPreferenceSource.praytime = prayerTime
        _flowPrayerTime.value = prayerTime
    }

    override fun getPrayerTime(): PrayerTime = sharedPreferenceSource.praytime
    override fun getNextPrayerTime(prayerTime: PrayerTime): NextPrayerTime {
        val untilPrayer = prayerTime.getTimeUntilNextPrayerString()
        return NextPrayerTime(
            textPrayerTime = prayerTime.getCurrentPrayerTimeString().orEmpty(),
            textUntil = untilPrayer.first,
            textPrayer = untilPrayer.second
        )
    }

    override fun getCurrentHijrDate(): String {
        val cal = UmmalquraCalendar()
        val year = cal.get(Calendar.YEAR)
        val month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return "$day $month $year"
    }

    override fun getUserCoordinate(): LatLng = sharedPreferenceSource.userCoordinates
}
