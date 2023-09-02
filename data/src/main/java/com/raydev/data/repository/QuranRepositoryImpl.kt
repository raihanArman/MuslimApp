package com.raydev.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raydev.anabstract.state.ResponseState
import com.raydev.data.database.entity.SurahEntity
import com.raydev.data.datasource.local.AyatLineLocalDataSource
import com.raydev.data.datasource.local.AyatLocalDataSource
import com.raydev.data.datasource.local.BookmarkQuranDataSource
import com.raydev.data.datasource.local.SurahLocalDataSource
import com.raydev.data.datasource.pref.SharedPreferenceSource
import com.raydev.data.mapper.mapToEntity
import com.raydev.data.mapper.mapToModel
import com.raydev.domain.repository.QuranRepository
import com.raydev.shared.database.entity.AyahLine
import com.raydev.shared.model.Ayah
import com.raydev.shared.model.AyahFromFile
import com.raydev.shared.model.Surah
import com.raydev.shared.util.FileUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

class QuranRepositoryImpl(
    private val ayahDataSource: AyatLocalDataSource,
    private val ayatLineDataSource: AyatLineLocalDataSource,
    private val surahDataSource: SurahLocalDataSource,
    private val bookmarkQuranDataSource: BookmarkQuranDataSource,
    private val sharedPreferenceSource: SharedPreferenceSource,
    private val context: Context,
) : QuranRepository {

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
                    val isBookmark = bookmarkQuranDataSource.checkBookmarkIsExists(
                        surahId = it.chapterId,
                        ayahId = it.verseNumber
                    )
                    it.mapToModel(isBookmark)
                }
            }
        }
    }

    override fun getAyahBySurahId(surahId: Int): Flow<List<Ayah>> {
        return ayahDataSource.getAyahBySurahId(surahId).map {
            it.map { ayah ->
                val isBookmark = bookmarkQuranDataSource.checkBookmarkIsExists(
                    surahId = ayah.chapterId,
                    ayahId = ayah.verseNumber
                )
                val lastReadValue = sharedPreferenceSource.quranLastRead
                val isLastRead = surahId == lastReadValue.surahId &&
                    ayah.verseNumber == lastReadValue.ayah
                ayah.mapToModel(isBookmark, isLastRead)
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
                        }
                    )
                    emit(ResponseState.Loading(i))
                }

                val juz1To5 = Gson().fromJson<ArrayList<AyahFromFile>>(
                    FileUtils.getJsonStringFromAssets(
                        context, "json/quran/quran1.json"
                    ) {},
                    object : TypeToken<ArrayList<AyahFromFile>>() {}.type,
                )
                val data = juz1To5.map { it.mapToEntity() }
                ayahDataSource.saveAyah(data)
                emit(ResponseState.Loading(606))

                val juz6To10 = Gson().fromJson<ArrayList<AyahFromFile>>(
                    FileUtils.getJsonStringFromAssets(
                        context, "json/quran/quran2.json"
                    ) {},
                    object : TypeToken<ArrayList<AyahFromFile>>() {}.type,
                )
                ayahDataSource.saveAyah(juz6To10.map { it.mapToEntity() })
                emit(ResponseState.Loading(607))

                val juz11To15 = Gson().fromJson<ArrayList<AyahFromFile>>(
                    FileUtils.getJsonStringFromAssets(
                        context, "json/quran/quran3.json"
                    ) {},
                    object : TypeToken<ArrayList<AyahFromFile>>() {}.type,
                )
                ayahDataSource.saveAyah(juz11To15.map { it.mapToEntity() })
                emit(ResponseState.Loading(608))

                val juz16To20 = Gson().fromJson<ArrayList<AyahFromFile>>(
                    FileUtils.getJsonStringFromAssets(
                        context, "json/quran/quran4.json"
                    ) {},
                    object : TypeToken<ArrayList<AyahFromFile>>() {}.type,
                )
                ayahDataSource.saveAyah(juz16To20.map { it.mapToEntity() })
                emit(ResponseState.Loading(609))

                val juz21To25 = Gson().fromJson<ArrayList<AyahFromFile>>(
                    FileUtils.getJsonStringFromAssets(
                        context, "json/quran/quran5.json"
                    ) {},
                    object : TypeToken<ArrayList<AyahFromFile>>() {}.type,
                )
                ayahDataSource.saveAyah(juz21To25.map { it.mapToEntity() })
                emit(ResponseState.Loading(610))

                val juz26To30 = Gson().fromJson<ArrayList<AyahFromFile>>(
                    FileUtils.getJsonStringFromAssets(
                        context, "json/quran/quran6.json"
                    ) {},
                    object : TypeToken<ArrayList<AyahFromFile>>() {}.type,
                )
                ayahDataSource.saveAyah(juz26To30.map { it.mapToEntity() })
                emit(ResponseState.Loading(611))

                if (ayahDataSource.getAyahCount() == 6236) {
                    setupSurah()
                } else
                    return@flow

                emit(ResponseState.Success(Unit))
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message))
            }
        }
    }

    private suspend fun setupSurah() {
        val surah = Gson().fromJson<ArrayList<SurahEntity>>(
            FileUtils.getJsonStringFromAssets(
                context,
                "json/quran/surah.json"
            ) {},
            object : TypeToken<ArrayList<SurahEntity>>() {}.type,
        )
        surahDataSource.saveSurah(surah)
    }
}
