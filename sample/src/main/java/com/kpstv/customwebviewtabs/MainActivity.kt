package com.kpstv.customwebviewtabs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kpstv.cwt.CWT
import com.kpstv.cwt.utils.sam.LoadState
import com.kpstv.cwt.utils.sam.OnPageLoadListener
import com.kpstv.cwt.utils.sam.OnPageLoadingListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnPageLoadListener, OnPageLoadingListener {

    companion object {
        const val ACTION_CANCEL = "action_cancel"
    }

    private lateinit var notification: NotificationCompat.Builder
    private var customWebViewTabs: CWT? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.setText("https://github.com/KaustubhPatange/CustomWebviewTabs")
    }

    fun buttonClick(view: View) {
        createNotification()

        if (!TextUtils.isEmpty(editText.text)) {
            // (Optional) You don't need to keep reference to the class
            // In this example I'm demonstrating the use of [cancel]
            customWebViewTabs = CWT.Builder(this)
                .onPageLoadListener(this)
                .onPageLoadingListener(this)
                .onWindowClosedListener {
                    cancelNotification()
                }
                .apply {
                    // Optionally set other settings

                    // lookFeel.primaryColor = ContextCompat.getColor(this, R.color.colorPrimary)
                    options.privateMode = checkbox_private_mode.isChecked
                    options.lockToolbarScrolling = lock_toolbar_scrolling.isChecked
                }
                .launch(editText.text.toString())
        }
    }

    override fun onLoading(progress: Int) {
        notification.setProgress(100, progress, false)
    }

    override fun onLoad(state: LoadState) {
        when (state) {
            is LoadState.Loaded -> {
                notification.setContentTitle(state.title)
                notification.setContentText(state.url)
                notification.setProgress(0, 0, false)
            }
            is LoadState.Loading -> {
                notification.setLargeIcon(state.favicon)
                notification.setContentText(state.url)
            }
        }
        updateNotification()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.action == ACTION_CANCEL)
            customWebViewTabs?.cancel()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            manager?.createNotificationChannel(
                NotificationChannel(
                    "channel_01",
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 100,
            Intent(this, MainActivity::class.java).apply {
                action = ACTION_CANCEL
            },
            0
        )

        notification = NotificationCompat.Builder(this, "channel_01")
            .setSmallIcon(R.drawable.ic_notify)
            .setProgress(0, 0, true)
            .setOngoing(true)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .addAction(R.mipmap.ic_launcher, "Cancel", pendingIntent)

        updateNotification()
    }

    private fun updateNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.notify(102, notification.build())
    }

    private fun cancelNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.cancel(102)
    }
}