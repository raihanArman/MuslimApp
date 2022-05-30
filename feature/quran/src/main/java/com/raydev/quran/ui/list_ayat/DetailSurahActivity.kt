package com.raydev.quran.ui.list_ayat

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.raydev.anabstract.base.BaseActivity
import com.raydev.quran.databinding.ActivityDetailSurahBinding
import com.raydev.quran.di.QuranModule.quranModule
import org.koin.core.context.loadKoinModules

import com.google.android.material.tabs.TabLayoutMediator
import com.raydev.quran.R
import com.raydev.quran.util.StatusFile
import com.raydev.quran.viewmodel.AyatViewModel
import com.raydev.shared.model.Surah
import com.raydev.quran.work.FileDownloadHelper
import io.karn.notify.Notify
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class DetailSurahActivity : BaseActivity<ActivityDetailSurahBinding>() {
    private  val TAG = "DetailSurahActivity"
    val viewModel: AyatViewModel by viewModel()
    val fileDownloadHelper: FileDownloadHelper by inject()
    var mediaPlayer: MediaPlayer? = null

    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach {
            Log.e("DEBUG", "${it.key} = ${it.value}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(quranModule)

        supportActionBar?.hide()

        getSurahListFromIntent()
        setupViewPager()
        setupObserveCurrentSurahPlay()
        setCurrentSurah()

        binding.ivDownload.setOnClickListener {
            downloadProcess()
        }

        binding.ivPlay.setOnClickListener {
            playAudio()
        }

        binding.ivPlaying.setOnClickListener {
            pauseAudio()
        }

        binding.ivPause.setOnClickListener {
            continueAudio()
        }

    }

    private fun pauseAudio(){
        if(mediaPlayer != null){
            mediaPlayer?.pause()
            viewModel.playAudioCurrentSurah(StatusFile.PAUSE)
            viewModel.mediaIsPause = true
        }
    }

    private fun continueAudio(){
        if(mediaPlayer != null){
            mediaPlayer?.start()
            viewModel.playAudioCurrentSurah(StatusFile.PLAYING)
            viewModel.mediaIsPause = false
        }
    }

    private fun playAudio(){
        if(checkPermission()) {
            val file = viewModel.getCurrentSurahAudioAtLocalStorage()
            if (mediaPlayer == null) {
                Log.d("DetailSurah", "playAudio: init")
                mediaPlayer = MediaPlayer.create(
                    this, Uri.parse(
                        file.path
                    )
                )
                mediaPlayer?.setOnPreparedListener { mp -> mp.start() }
            }
            mediaPlayer?.start()
            viewModel.playAudioCurrentSurah(StatusFile.PLAYING)
            viewModel.mediaIsPlay = true
        }
    }

    private fun downloadProcess(){
        if(checkPermission()) {
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage("Audio murottal ${viewModel.surahCurrentSelected?.nama}, unduh terlebih dahulu")
            dialog.setPositiveButton("Iya") { p0, p1 ->
                //Download
                val downloadNotificationBuilder = Notify.with(this)
                    .header {
                        icon = R.drawable.ic_app_icon
                    }
                    .content {
                        title = "Downloading..."
                        text = "Mohon tunggu, file sedang di persiapkan"
                    }
                    .progress {
                        showProgress = true
                        enablePercentage = true
                        progressPercent = 0
                    }
                    .asBuilder()

                downloadNotificationBuilder.setOngoing(true)
                fileDownloadHelper.startDownloadingFile(viewModel.surahCurrentSelected!!,
                    success = {
                        downloadNotificationBuilder.setProgress(0, 0, false)
                        showCustomNotification(
                            R.string.notif_success_title,
                            R.string.notif_success_text,
                            R.color.alert_success
                        )
                        viewModel.checkFileCurrentSurah()
                    },
                    failed = {
                        showCustomNotification(
                            R.string.notif_failed_title,
                            R.string.notif_failed_text,
                            R.color.alert_danger
                        )
                    },
                    running = {
                        showCustomNotification(
                            R.string.notif_running_title,
                            R.string.notif_running_value,
                            R.color.alert_primary,
                        )
                    }, this
                )
            }
            dialog.setNegativeButton(
                "Batal"
            ) { p0, p1 -> p0?.dismiss() }
            dialog.show()
        }
    }

    private fun setupObserveCurrentSurahPlay(){
        viewModel.observableStatusPlayAudioCurrentSurah.observe(this) { status ->
            when (status) {
                StatusFile.NOT_DOWNLOADED -> {
                    binding.ivDownload.visibility = View.VISIBLE
                    binding.ivPlay.visibility = View.GONE
                    binding.ivPlaying.visibility = View.GONE
                    binding.ivPause.visibility = View.GONE
                }
                StatusFile.NOT_PLAY -> {
                    binding.ivDownload.visibility = View.GONE
                    binding.ivPlay.visibility = View.VISIBLE
                    binding.ivPlaying.visibility = View.GONE
                    binding.ivPause.visibility = View.GONE
                }
                StatusFile.PLAYING -> {
                    binding.ivDownload.visibility = View.GONE
                    binding.ivPlay.visibility = View.GONE
                    binding.ivPlaying.visibility = View.VISIBLE
                    binding.ivPause.visibility = View.GONE
                }
                StatusFile.PAUSE -> {
                    binding.ivDownload.visibility = View.GONE
                    binding.ivPlay.visibility = View.GONE
                    binding.ivPlaying.visibility = View.GONE
                    binding.ivPause.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getSurahListFromIntent() {
        val surahList = intent.getParcelableArrayListExtra<Surah>("surah_list") as ArrayList<Surah>
        val number = intent.getIntExtra("number", 0)

        viewModel.setSurahList(surahList)
        if(number != 0)
            viewModel.setCurrentSurahPagerIndex(number - 1)
        else
            viewModel.setCurrentSurahPagerIndex(number)
    }

    private fun setupViewPager(){
        val adapter = ViewPagerAdapter(this)
        adapter.setSurahTabsList(viewModel.surahList)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(
            binding.tabs, binding.viewpager
        ) { tab, position -> tab.text = viewModel.surahList[position].nama }.attach()
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentSurah(position)
                viewModel.loadAyat((position+1).toString())
                viewModel.checkFileCurrentSurah()
                viewModel.setInitPlayAudioCurrentSurah()
            }
        })
        binding.viewpager
    }

    private fun setCurrentSurah(){
        binding.viewpager.currentItem = viewModel.currentIndexSurahPager
    }

    private fun checkPermission(): Boolean{
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestMultiplePermissions.launch(
                PERMISSIONS_STORAGE
            )

            return false
        }else{
            return true
        }
    }


    override fun getViewBinding() = ActivityDetailSurahBinding.inflate(layoutInflater)

}