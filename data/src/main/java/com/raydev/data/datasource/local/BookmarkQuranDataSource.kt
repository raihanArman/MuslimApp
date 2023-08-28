package com.raydev.data.datasource.local

import com.raydev.data.database.dao.BookmarkQuranDao
import com.raydev.data.database.entity.BookmarkQuranEntity

/**
 * @author Raihan Arman
 * @date 26/08/23
 */
class BookmarkQuranDataSource(
    private val bookmarkQuranDao: BookmarkQuranDao
) {
    fun getBookmarks() = bookmarkQuranDao.getBookmarks()
    fun checkBookmarkIsExists(surahId: Int, ayahId: Int) =
        bookmarkQuranDao.getBookmarkBySurahAndAyah(surahId, ayahId) != null

    suspend fun deleteBookmark(surahId: Int, ayahId: Int) =
        bookmarkQuranDao.deleteBookmark(surahId, ayahId)

    suspend fun saveBookmark(bookmarkQuranEntity: BookmarkQuranEntity) =
        bookmarkQuranDao.saveBookmark(bookmarkQuranEntity)
}
