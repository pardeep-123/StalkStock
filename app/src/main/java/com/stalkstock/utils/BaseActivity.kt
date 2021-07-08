package com.stalkstock.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.utils.others.Util

abstract class BaseActivity : AppCompatActivity() {
    var self: Context? = null
    lateinit var mUtils: Util
    protected abstract fun getContentId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(getContentId())
        mUtils = Util()
        self = this@BaseActivity
    }

    // show keyboard
    protected fun showKeyBoard(view: View?) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    // hide keyboard
    protected fun hideKeyboard(view: View?) {
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showToast(msg:String)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}