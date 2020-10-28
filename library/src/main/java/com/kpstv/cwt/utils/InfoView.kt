package com.kpstv.cwt.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.kpstv.cwt.R
import com.kpstv.cwt.data.Website
import com.kpstv.cwt.databinding.SheetTopBinding

class InfoView private constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(
    context,
    attributes,
    defStyleAttr
) {
    private lateinit var container: ViewGroup
    private var animatorView: View? = null
    private var website: Website? = null

    @SuppressLint("ClickableViewAccessibility")
    private val onParentTouchListener = OnTouchListener { _, event ->
        cancel()
        false
    }

    data class Builder(private val container: ViewGroup) {
        private val infoView = InfoView(container.context)

        fun runAlphaAnimationOn(view: View): Builder {
            infoView.animatorView = view
            return this
        }

        fun setData(website: Website): Builder {
            infoView.website = website
            return this
        }

        fun show() {
            infoView.container = container

            val animation = AlphaAnimation(1f, 0.3f).apply {
                duration = 500
                fillAfter = true
            }
            infoView.animatorView?.startAnimation(animation)

            infoView.setOnTouchListener(infoView.onParentTouchListener)
            container.addView(infoView)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val view = inflate(context, R.layout.sheet_top, this)
        SheetTopBinding.bind(view).apply {
            title.text = website?.title
            subtitle.text = website?.url
        }

        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up)
    }

    private fun cancel() {
        setOnTouchListener(null)
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_up)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                container.removeView(this@InfoView)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        this.startAnimation(animation)

        animatorView?.startAnimation(AlphaAnimation(0.3f, 1f).apply {
            duration = 500
            fillAfter = true
        })
    }
}