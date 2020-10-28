package com.kpstv.customwebviewtabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.kpstv.cwt.CWT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.setText("https://link.medium.com/oLwagghmVab")
    }

    fun buttonClick(view: View) {
        if (!TextUtils.isEmpty(editText.text)) {
            CWT.Builder(this)
                .launch(editText.text.toString())
        }
    }
}