package app.vazovsky.lesson_9_klyueva

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.vazovsky.lesson_9_klyueva.presentation.MainActivity
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class DownloadService : Service() {
    companion object {
        const val EXTRA_URL = "extra_url"
        const val DATA_PROGRESS = "data_progress"
        const val EXTRA_IMAGE = "extra_image"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, DownloadService::class.java)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            val downloadPath = downloadArchive(intent)
            if (downloadPath == "") {
                Log.d("LOL", "Все хуйня")
            }
            val unzippingPath = unzip(downloadPath)

            val intentImage = Intent(MainActivity.ACTION_IMAGE)
            intentImage.putExtra(EXTRA_IMAGE, unzippingPath)
            sendBroadcast(intentImage)
        }.start()
        return START_REDELIVER_INTENT
    }

    private fun downloadArchive(intent: Intent?): String {
        val urlToDownload = intent?.getStringExtra(EXTRA_URL)
        val intentDownload = Intent(MainActivity.ACTION_DOWNLOAD)
        var path = ""
        try {
            val url = URL(urlToDownload)
            val connection = url.openConnection()
            connection.connect()

            val fileLength = connection.contentLength
            val input = BufferedInputStream(connection.getInputStream())
            val file = File(this.applicationContext.filesDir, "archive.zip")
            path = file.absolutePath.toString()
            if (!file.exists()) {
                file.createNewFile()
            }
            val output = FileOutputStream(file)
            val data = byteArrayOf(1024.toByte())
            var total = 0
            var count: Int
            while (input.read(data).also { count = it } != -1) {
                total += count
                intentDownload.putExtra(DATA_PROGRESS, (total * 100 / fileLength))
                sendBroadcast(intentDownload)
                output.write(data, 0, count)
            }
            output.flush()
            output.close()
            input.close()

            intentDownload.putExtra(DATA_PROGRESS, 100)
            sendBroadcast(intentDownload)
        } catch (e: Exception) {
            Log.d("LOL", "что не так: ${e.message}")
            e.printStackTrace()
        }
        return path
    }

    private fun unzip(downloadPath: String): String {
        var path = ""
        try {
            val input = ZipInputStream(FileInputStream(downloadPath))
            var entry: ZipEntry
            val data = byteArrayOf(1024.toByte())
            var count: Int
            while (input.nextEntry.also {
                    entry = it
                } != null) {
                val filename = downloadPath + entry.name
                path = filename
                val output = FileOutputStream(filename)
                while (input.read(data).also { count = it } != -1) {
                    output.write(data, 0, count)
                }
                output.close()
                input.closeEntry()
                input.close()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return path
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
