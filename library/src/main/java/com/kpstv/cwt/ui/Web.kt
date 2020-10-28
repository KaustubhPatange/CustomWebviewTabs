package com.kpstv.cwt.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kpstv.cwt.R
import com.kpstv.cwt.data.Website
import com.kpstv.cwt.databinding.ActivityWebBinding
import com.kpstv.cwt.utils.*
import com.kpstv.cwt.utils.sam.LoadState
import kotlinx.android.synthetic.main.activity_web.*

class Web : AppCompatActivity() {

    companion object {
        private const val ARG_URL = "arg_url"
        private const val PREVIOUS_URL = "previous_url"

        fun launch(context: Context, intent: Intent, url: String) {
            intent.putExtra(ARG_URL, url)
            context.startActivity(intent)
        }
    }

    private val binding by viewBinding(ActivityWebBinding::inflate)

    private val menuDelegates by lazy { MenuDelegates(this) }
    private var website = Website()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        website.url = intent?.getStringExtra(ARG_URL) ?: run { finish(); return }


        setAppbar()
        if (OptionDelegates.options.privateMode)
            clearWebViewData()
        setWebView(savedInstanceState)
        setSwipeRefreshLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuDelegates.createOptionsMenu(menu!!)

    override fun onOptionsItemSelected(item: MenuItem) = menuDelegates.handleItemSelected(item)

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(PREVIOUS_URL, website.url)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()
    }

    override fun onDestroy() {
        webView.destroy()
        OptionDelegates.removeAllListener()
        super.onDestroy()
    }

    internal fun getCurrentUrl() = intent.getStringExtra(ARG_URL)!!

    @RequiresApi(Build.VERSION_CODES.Q)
    internal fun applyForceDark(checked: Boolean) {
        binding.webView.settings.forceDark =
            if (checked) WebSettings.FORCE_DARK_ON else WebSettings.FORCE_DARK_OFF
    }

    internal fun showWebInfo() {
        showWebsiteInfo()
    }

    private fun setAppbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.cwt_ic_close)
            title = " "
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        ThemeDelegates.apply(binding.toolbar)
        ThemeDelegates.apply(binding.progressBar)
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.webView.reload()
        }
    }

    @SuppressLint("NewApi")
    private fun setWebView(savedInstanceState: Bundle?) {
        binding.webView.apply {
            applyForceDark(OptionDelegates.options.darkMode)

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.toolbarHeader.text = Uri.parse(url).host

                    OptionDelegates.pageLoadListener?.onLoad(LoadState.Loading(url, favicon))
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.webView.show()
                    binding.progressBar.hide()

                    OptionDelegates.pageLoadListener?.onLoad(LoadState.Loaded(website.url, website.title))
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    website.title = title ?: ""
                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.progressBar.show()
                    binding.progressBar.progress = newProgress
                    if (binding.progressBar.progress >= 99)
                        binding.progressBar.progress = 0

                    OptionDelegates.pageLoadingListener?.onLoading(newProgress)
                }
            }
            settings.javaScriptEnabled = true
            val previousUrl = savedInstanceState?.getString(PREVIOUS_URL)
            if (previousUrl == null) {
                loadUrl(website.url)
            } else {
                loadUrl(previousUrl)
            }
        }
    }

    private fun clearWebViewData() {
        CookieManager.getInstance().setAcceptCookie(false)
        binding.webView.clearCache(true)
        binding.webView.clearHistory()

        binding.webView.clearFormData()
    }

    private fun showWebsiteInfo() {
        InfoView.Builder(window.decorView as ViewGroup)
            .setData(website)
            .runAlphaAnimationOn(binding.webView)
            .show()
    }
}