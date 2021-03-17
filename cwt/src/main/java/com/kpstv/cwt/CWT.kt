package com.kpstv.cwt

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kpstv.cwt.data.LookFeel
import com.kpstv.cwt.data.Options
import com.kpstv.cwt.ui.Web
import com.kpstv.cwt.utils.LibraryUtils
import com.kpstv.cwt.utils.OptionDelegates
import com.kpstv.cwt.utils.ThemeDelegates
import com.kpstv.cwt.utils.sam.OnPageLoadListener
import com.kpstv.cwt.utils.sam.OnPageLoadingListener
import com.kpstv.cwt.utils.sam.OnWindowClosedListener

class CWT private constructor(private val context: Context) {

    fun cancel() {
        LocalBroadcastManager.getInstance(context).sendBroadcast(
            Intent(Web.ACTION_CANCEL)
        )
    }

    data class Builder(private val context: Context) {
        val lookFeel = LookFeel.defaults(context)
        val options = Options.defaults()

        val intent = LibraryUtils.getIntentDefaults(context)

        fun onPageLoadListener(listener: OnPageLoadListener): Builder {
            OptionDelegates.pageLoadListener = listener
            return this
        }

        fun onPageLoadingListener(listener: OnPageLoadingListener): Builder {
            OptionDelegates.pageLoadingListener = listener
            return this
        }

        fun onWindowClosedListener(listener: OnWindowClosedListener): Builder {
            OptionDelegates.windowClosedListener = listener
            return this
        }

        fun launch(url: String): CWT {
            ThemeDelegates.lookFeel = lookFeel
            OptionDelegates.options = options

            Web.launch(context, intent, url)

            return CWT(context)
        }
    }
}