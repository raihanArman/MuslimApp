package com.raydev.data.repository

import android.content.Context
import com.raydev.anabstract.state.ResponseState
import com.raydev.data.datasource.local.AyatLocalDataSource
import com.raydev.data.datasource.local.BookmarkQuranDataSource
import com.raydev.data.datasource.local.SurahLocalDataSource
import com.raydev.data.mapper.mapToEntity
import com.raydev.data.mapper.mapToModel
import com.raydev.domain.repository.BookmarkRepository
import com.raydev.shared.model.BookmarkQuran
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
class BookmarkRepositoryImpl(
    private val context: Context,
    private val ayatLocalDataSource: AyatLocalDataSource,
    private val surahLocalDataSource: SurahLocalDataSource,
    private val bookmarkQuranDataSource: BookmarkQuranDataSource
): BookmarkRepository {
    override fun getBookmarks(): Flow<ResponseState<List<BookmarkQuran>>> = flow {
        emit(ResponseState.Loading())
        val bookmarks = bookmarkQuranDataSource.getBookmarks().first().map {
            val surah = surahLocalDataSource.getSurahByIdSingle(it.surahId).first()?.mapToModel(context)
            val ayah = ayatLocalDataSource.getAyahByVerseNumber(it.ayahId).first()?.mapToModel()
            it.mapToModel(surah = surah, ayah = ayah)
        }

        if (bookmarks.isNotEmpty())
            emit(ResponseState.Success(bookmarks))
        else
            emit(ResponseState.Empty)
    }

    override suspend fun deleteBookmark(surahId: Int, ayahId: Int) {
        bookmarkQuranDataSource.deleteBookmark(surahId, ayahId)
    }

    override suspend fun saveBookmark(bookmarkQuran: BookmarkQuran) {
        bookmarkQuranDataSource.saveBookmark(bookmarkQuran.mapToEntity())
    }

    override suspend fun checkIsBookmark(surahId: Int, ayahId: Int): Boolean {
        return bookmarkQuranDataSource.checkBookmarkIsExists(surahId, ayahId)
    }
}