package app.vazovsky.lesson_9_klyueva

import android.app.DownloadManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Job


class DownloadService : Service() {
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, DownloadService::class.java)
        }
    }

    private var imageJob: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = "https://wdfiles.ru/2833a2"
        val name = "archive.zip"
        Log.d("LOL", "loadImage")

        downloadFile(name, url)
//        return super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun downloadFile(name: String, url: String?) {
//        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChanel("my_service", "My Background Service")
//        } else {
//            ""
//        }
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setTitle("My File")
        request.setDescription("Downloading")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationUri(Uri.parse("file://$name"))
        downloadManager.enqueue(request)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}
