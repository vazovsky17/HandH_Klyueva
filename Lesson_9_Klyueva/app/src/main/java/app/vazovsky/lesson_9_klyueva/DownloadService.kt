package app.vazovsky.lesson_9_klyueva

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import app.vazovsky.lesson_9_klyueva.presentation.MainActivity
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.URL


class DownloadService : Service() {
    companion object {
        const val EXTRA_URL = "extra_url"
        const val DATA_PROGRESS = "data_progress"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, DownloadService::class.java)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val urlToDownload = intent?.getStringExtra(EXTRA_URL)
        val intent = Intent(MainActivity.ACTION)
        Log.d("LOL", "Download")
        Thread {
            try {
                val url = URL(urlToDownload)
                val connection = url.openConnection()
                Log.d("LOL", "Connection")
                connection.connect()

                val fileLength = connection.contentLength
                val input = BufferedInputStream(connection.getInputStream())
                val path = "${Environment.DIRECTORY_DOWNLOADS}/archive.zip"
                val output = FileOutputStream(path)
                val data = byteArrayOf(1024.toByte())
                var total = 0
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    total += count
                    intent.putExtra(DATA_PROGRESS, (total * 100 / fileLength))
                    sendBroadcast(intent)
                    output.write(data, 0, count)
                }
                output.flush()
                output.close()
                input.close()
            } catch (e: Exception) {

            }

        }
        intent.putExtra(DATA_PROGRESS, 100)
        sendBroadcast(intent)

        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
