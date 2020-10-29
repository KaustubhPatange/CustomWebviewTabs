package com.kpstv.cwt.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.kpstv.cwt.R
import com.kpstv.cwt.data.LookFeel

internal object ThemeDelegates {
    private var theme: LookFeel? = null
    var lookFeel: LookFeel
        get() = theme
            ?: throw UninitializedPropertyAccessException("lookFeel property is not initialized")
        set(value) {
            theme = value
        }

    fun apply(toolbar: Toolbar) {
        toolbar.setBackgroundColor(lookFeel.primaryColor)
    }

    fun apply(progressBar: ProgressBar) {
        progressBar.progressDrawable.colorFilter =
            PorterDuffColorFilter(
                progressBar.context.colorFrom(R.color.white), PorterDuff.Mode.SRC_ATOP
            )
    }
}