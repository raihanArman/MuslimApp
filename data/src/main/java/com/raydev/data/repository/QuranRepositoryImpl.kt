package com.raydev.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raydev.data.datasource.remote.QuranRemoteDataSource
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.Surah
import com.raydev.anabstract.state.ResponseState
import com.raydev.data.datasource.local.AyatLineLocalDataSource
import com.raydev.data.datasource.local.AyatLocalDataSource
import com.raydev.data.datasource.local.SurahLocalDataSource
import com.raydev.data.mapper.mapToModel
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.database.entity.AyahLine
import com.raydev.shared.database.entity.AyatEntity
import com.raydev.shared.database.entity.SurahEntity
import com.raydev.shared.util.FileUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

class QuranRepositoryImpl(
    private val remoteDataSource: QuranRemoteDataSource,
    private val ayahDataSource: AyatLocalDataSource,
    private val ayatLineDataSource: AyatLineLocalDataSource,
    private val surahDataSource: SurahLocalDataSource,
    private val context: Context,
): QuranRepository {

    override fun getSurah(): Flow<List<Surah>> = surahDataSource.getSurah().map {
        it.map { surah ->
            surah.mapToModel(context)
        }
    }

    override fun getSurahAyah(): Flow<List<Surah>> = surahDataSource.getSurah().map {
        it.map { surah ->
            val ayah = ayahDataSource.getAyahBySurahId(surah.id).last()
            surah.mapToModel(context).apply {
                listAyah = ayah.map {
                    it.mapToModel()
                }
            }
        }
    }


    override fun getAyahBySurahId(surahId: Int): Flow<List<Ayah>> {
        return ayahDataSource.getAyahBySurahId(surahId).map {
            it.map { ayah ->
                ayah.mapToModel()
            }
        }
    }

    override fun setupQuran(): Flow<ResponseState<Unit>> {
        return flow {
            try {
                ayahDataSource.deleteAyah()
                ayatLineDataSource.delete()
                for (i in 1..604) {
                    ayatLineDataSource.saveAyahLine(
                        Gson().fromJson<ArrayList<AyahLine>>(
                            FileUtils.getJsonStringFromAssets(
                                context,
                                "json/quran/paged/page$i.json"
                            ) {},
                            object : TypeToken<ArrayList<AyahLine>>() {}.type,
                        ).map {
                            it.apply {
                                this.page = i
                            }
                        })
                    emit(ResponseState.Loading(i))
                }

                val surah = Gson().fromJson<ArrayList<SurahEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/surah.json") {}, object : TypeToken<ArrayList<SurahEntity>>() {}.type,)
                surahDataSource.saveSurah(surah)
                emit(ResponseState.Loading(605))

                val juz1_5 = Gson().fromJson<ArrayList<AyatEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/quran1.json") {}, object : TypeToken<ArrayList<AyatEntity>>() {}.type,)
                ayahDataSource.saveAyah(juz1_5)
                emit(ResponseState.Loading(606))

                val juz6_10 = Gson().fromJson<ArrayList<AyatEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/quran2.json") {}, object : TypeToken<ArrayList<AyatEntity>>() {}.type,)
                ayahDataSource.saveAyah(juz6_10)
                emit(ResponseState.Loading(607))

                val juz11_15 = Gson().fromJson<ArrayList<AyatEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/quran3.json") {}, object : TypeToken<ArrayList<AyatEntity>>() {}.type,)
                ayahDataSource.saveAyah(juz11_15)
                emit(ResponseState.Loading(608))

                val juz16_20 = Gson().fromJson<ArrayList<AyatEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/quran4.json") {}, object : TypeToken<ArrayList<AyatEntity>>() {}.type,)
                ayahDataSource.saveAyah(juz16_20)
                emit(ResponseState.Loading(609))

                val juz21_25 = Gson().fromJson<ArrayList<AyatEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/quran5.json") {}, object : TypeToken<ArrayList<AyatEntity>>() {}.type,)
                ayahDataSource.saveAyah(juz21_25)
                emit(ResponseState.Loading(610))

                val juz26_30 = Gson().fromJson<ArrayList<AyatEntity>>(FileUtils.getJsonStringFromAssets(context, "json/quran/quran6.json") {}, object : TypeToken<ArrayList<AyatEntity>>() {}.type,)
                ayahDataSource.saveAyah(juz26_30)
                emit(ResponseState.Loading(611))

                if (ayahDataSource.getAyahCount() != 6236){
                     return@flow
                }

                emit(ResponseState.Success(Unit))

            } catch (e: Exception) {
                emit(ResponseState.Error(e.message))
            }
        }
    }

}