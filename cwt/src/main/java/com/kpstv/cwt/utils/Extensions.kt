package com.kpstv.cwt.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding

internal fun View.hide() {
    visibility = View.GONE
}

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.show() {
    visibility = View.VISIBLE
}

internal fun Int.dp(): Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (this * displayMetrics.density + 0.5).toInt()
}

@ColorInt
internal fun Context.colorFrom(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

internal fun Context.drawableFrom(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)

internal fun Context.toast(@StringRes id: Int): Unit =
    Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }