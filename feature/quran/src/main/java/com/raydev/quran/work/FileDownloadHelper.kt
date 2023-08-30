package com.raydev.quran.work

import androidx.lifecycle.LifecycleOwner
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.raydev.quran.util.FileParams

class FileDownloadHelper(
    private val workManager: WorkManager
) {
    fun startDownloadingFile(
        success: (String) -> Unit,
        failed: (String) -> Unit,
        running: () -> Unit,
        lifecycleOwner: LifecycleOwner
    ) {
        val data = Data.Builder()

//        data.apply {
//            putString(FileParams.KEY_FILE_NAME, "${file.nama}.mp3")
//            putString(FileParams.KEY_FILE_URL, file.audio)
//            putString(FileParams.KEY_FILE_TYPE, "MP3")
//        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            fileDownloadWorker
        )

        workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
            .observe(lifecycleOwner) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            success(it.outputData.getString(FileParams.KEY_FILE_URI) ?: "")
                        }
                        WorkInfo.State.FAILED -> {
                            failed("Downloading failed!")
                        }
                        WorkInfo.State.RUNNING -> {
                            running()
                        }
                        else -> {
                            failed("Something went wrong")
                        }
                    }
                }
            }
    }
}
