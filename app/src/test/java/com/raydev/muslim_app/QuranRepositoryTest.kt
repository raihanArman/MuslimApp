package com.raydev.muslim_app

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.nhaarman.mockitokotlin2.whenever
import com.raydev.anabstract.state.ResponseState
import com.raydev.anabstract.util.LiveDataTestUtil
import com.raydev.data.datasource.remote.QuranRemoteDataSource
import com.raydev.shared.model.Surah
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito
import com.raydev.anabstract.util.fold
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule

@ExperimentalCoroutinesApi
class QuranRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(QuranRemoteDataSource::class.java)
    private val repository = FakeQuranRepositoryTest(remote)

    @Test
    fun `list surah is not null`() = runTest {
        val arraySurah = ArrayList<Surah>()

        Mockito.`when`(remote.getListSurah()).thenReturn(arraySurah)
        remote.getListSurah()

        val data = repository.getSurah()
        assertNotNull(data)
    }
}