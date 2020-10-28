package com.kpstv.cwt.utils

import android.app.Activity
import android.content.*
import android.net.Uri
import androidx.core.app.ShareCompat
import com.kpstv.cwt.R
import com.kpstv.cwt.ui.Web

class LibraryUtils {
   companion object {
       fun getIntentDefaults(context: Context): Intent = Intent(context, Web::class.java).apply {
           flags = Intent.FLAG_ACTIVITY_NEW_TASK
       }

       fun shareText(activity: Activity, text: String) {
           ShareCompat.IntentBuilder.from(activity)
               .setType("text/plain")
               .setText(text)
               .setChooserTitle("Share")
               .startChooser()
       }

       fun launchIntentActionView(context: Context, url: String) {
           try {
               context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                   data = Uri.parse(url)
                   flags = Intent.FLAG_ACTIVITY_NEW_TASK
               })
           }catch (e: ActivityNotFoundException) {
               context.toast(R.string.err_resolve_activity)
           }
       }

       fun copyToClipboard(context: Context, text: String): Boolean {
           val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
           manager?.setPrimaryClip(ClipData.newPlainText(text, text))
           return manager != null
       }
   }
}