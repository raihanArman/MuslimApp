package com.raydev.domain.repository

import com.raydev.anabstract.state.ResponseState
import com.raydev.shared.model.BookmarkQuran
import kotlinx.coroutines.flow.Flow

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
interface BookmarkRepository {
    fun getBookmarks(): Flow<ResponseState<List<BookmarkQuran>>>
    suspend fun deleteBookmark(surahId: Int, ayahId: Int)
    suspend fun saveBookmark(bookmarkQuran: BookmarkQuran)
    suspend fun checkIsBookmark(surahId: Int, ayahId: Int): Boolean
}
