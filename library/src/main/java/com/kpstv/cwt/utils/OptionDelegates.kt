package com.kpstv.cwt.utils

import androidx.annotation.DrawableRes
import com.kpstv.cwt.R
import com.kpstv.cwt.data.Options
import com.kpstv.cwt.utils.sam.OnPageLoadListener
import com.kpstv.cwt.utils.sam.OnPageLoadingListener

object OptionDelegates {
    private var setting: Options? = null
    var options: Options
        get() = setting
            ?: throw UninitializedPropertyAccessException("setting property is not initialized")
        set(value) { setting = value }

    var pageLoadListener: OnPageLoadListener? = null
    var pageLoadingListener: OnPageLoadingListener? = null

    fun removeAllListener() {
        pageLoadListener = null
        pageLoadingListener = null
    }

    @DrawableRes
    fun retrieveInfoIcon(): Int =
        if (options.privateMode) R.drawable.cwt_ic_incognito else R.drawable.cwt_ic_info
}