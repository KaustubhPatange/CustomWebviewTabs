package com.kpstv.cwt.data

import androidx.annotation.RequiresApi

data class Options(
    /**
     * Enables private browsing by clearing all cache, cookies & not at all maintaining
     * any sort of history.
     */
    var privateMode: Boolean,

    /**
     * Set if the default webView should render in [forceDark] mode.
     */
    @RequiresApi(29)
    var darkMode: Boolean
) {
    companion object {
        fun defaults() = Options(
            privateMode = false,
            darkMode = false
        )
    }
}