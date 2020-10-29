package com.kpstv.cwt.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import com.kpstv.cwt.R
import com.kpstv.cwt.ui.Web

class MenuDelegates(
    private val activity: Activity
) {
    private val currentUrl: String
        get() = (activity as Web).getCurrentUrl()

    fun createOptionsMenu(menu: Menu): Boolean {
        with(menu) {
            add(0, R.id.menu_show_info, Menu.NONE, R.string.show_info).apply {
                icon = activity.drawableFrom(OptionDelegates.retrieveInfoIcon())
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
            add(0, R.id.menu_copy_link, Menu.NONE, R.string.copy_link)
            add(0, R.id.menu_open_with, Menu.NONE, R.string.open_with)
            add(0, R.id.menu_share, Menu.NONE, R.string.share)
            if (Build.VERSION.SDK_INT >= 29)
                add(1, R.id.menu_forceDark, Menu.NONE, R.string.force_dark).apply {
                    isCheckable = true
                    isChecked = OptionDelegates.options.darkMode
                }
        }
        return true
    }

    @SuppressLint("NewApi")
    fun handleItemSelected(item: MenuItem): Boolean {
        item.isChecked = !item.isChecked
        when (item.itemId) {
            R.id.menu_copy_link -> {
                if (LibraryUtils.copyToClipboard(activity, currentUrl))
                    activity.toast(R.string.copy_clipboard)
            }
            R.id.menu_open_with -> LibraryUtils.launchIntentActionView(activity, currentUrl)
            R.id.menu_share -> LibraryUtils.shareText(activity, currentUrl)
            R.id.menu_forceDark -> (activity as Web).applyForceDark(item.isChecked)
            R.id.menu_show_info -> (activity as Web).showWebInfo()
        }
        return true
    }
}