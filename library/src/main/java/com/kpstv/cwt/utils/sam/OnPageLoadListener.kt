package com.kpstv.cwt.utils.sam

import android.graphics.Bitmap

fun interface OnPageLoadListener {
    fun onLoad(state: LoadState)
}


sealed class LoadState {
    data class Loading(val url: String?, val favicon: Bitmap?): LoadState()
    data class Loaded(val url: String, val title: String): LoadState()
}