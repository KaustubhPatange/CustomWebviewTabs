package com.kpstv.cwt.data

import android.content.Context
import androidx.annotation.ColorInt
import com.kpstv.cwt.R
import com.kpstv.cwt.utils.colorFrom

data class LookFeel(
    @ColorInt var primaryColor: Int
) {
    companion object {
        fun defaults(context: Context) = LookFeel(
            primaryColor = context.colorFrom(R.color.defaultColorPrimary)
        )
    }
}