package com.example.disneysolitaireai

import android.app.*
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.IBinder
import android.os.Build

class CaptureService : Service() {
    private var projection: MediaProjection? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channel = "solitaire_capture"
        if (Build.VERSION.SDK_INT >= 26) {
            getSystemService(NotificationManager::class.java).createNotificationChannel(
                NotificationChannel(channel, "Game screen capture", NotificationManager.IMPORTANCE_LOW))
        }
        val notification = if (Build.VERSION.SDK_INT >= 26)
            Notification.Builder(this, channel).setContentTitle("Disney Solitaire AI")
                .setContentText("화면 캡처가 활성화되어 있습니다.")
                .setSmallIcon(android.R.drawable.ic_menu_view).build()
        else Notification.Builder(this).setContentTitle("Disney Solitaire AI")
            .setSmallIcon(android.R.drawable.ic_menu_view).build()
        startForeground(11, notification)
        val code = intent?.getIntExtra("resultCode", 0) ?: 0
        val data = if (Build.VERSION.SDK_INT >= 33)
            intent?.getParcelableExtra("data", Intent::class.java)
        else @Suppress("DEPRECATION") (intent?.getParcelableExtra("data") as? Intent)
        if (data != null) {
            val mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            projection = mgr.getMediaProjection(code, data)
            // TODO: Register MediaProjection.Callback and ImageReader for frame acquisition.
        }
        return START_NOT_STICKY
    }
    override fun onDestroy() { projection?.stop(); projection = null; super.onDestroy() }
    override fun onBind(intent: Intent?): IBinder? = null
}
