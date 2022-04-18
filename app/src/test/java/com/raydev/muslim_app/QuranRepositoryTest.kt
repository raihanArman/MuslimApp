package com.raydev.muslim_app

import android.util.Log
import androidx.lifecycle.asLiveData
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

class QuranRepositoryTest {
    private val remote = Mockito.mock(QuranRemoteDataSource::class.java)
    private val repository = FakeQuranRepositoryTest(remote)

    val data: ArrayList<Surah> = ArrayList<Surah>()

    @Test
    fun getSurah(){
        val myScope = GlobalScope
        runBlocking {
            myScope.launch {
                val surahEntities = LiveDataTestUtil.getValue(repository.getSurah().asLiveData())
                Mockito.verify(remote).getListSurah()
                assertNotNull(surahEntities.data)
                assertEquals(surahEntities, ResponseState.Success(data))
            }
        }
    }
}