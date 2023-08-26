package com.raydev.domain.usecase.quran

import com.raydev.domain.repository.BookmarkRepository
import com.raydev.shared.model.BookmarkQuran
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookmarkAyahUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(bookmarkQuran: BookmarkQuran): Flow<Boolean> {
        return flow {
            val checkBookmark = bookmarkRepository.checkIsBookmark(
                surahId = bookmarkQuran.surah?.id ?: 0,
                ayahId = bookmarkQuran.ayah?.verseNumber ?: 0
            )

            println("CheckBookmark -> $checkBookmark")

            if (checkBookmark) {
                bookmarkRepository.deleteBookmark(bookmarkQuran)
                emit(false)
            } else {
                bookmarkRepository.saveBookmark(bookmarkQuran)
                emit(true)
            }
        }
    }
}