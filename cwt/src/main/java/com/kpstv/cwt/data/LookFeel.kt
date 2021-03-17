package com.kpstv.cwt.data

import android.content.Context
import androidx.annotation.ColorInt
import com.kpstv.cwt.R
import com.kpstv.cwt.utils.colorFrom
import androidx.appcompat.widget.Toolbar

data class LookFeel(
    /**
     * Modifies the view associates with `colorPrimary` color from the theme. eg: [Toolbar]
     */
    @ColorInt var primaryColor: Int
) {
    companion object {
        fun defaults(context: Context) = LookFeel(
            primaryColor = context.colorFrom(R.color.defaultColorPrimary)
        )
    }
}