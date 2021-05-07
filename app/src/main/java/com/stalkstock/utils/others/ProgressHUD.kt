package com.mender.utlis

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.stalkstock.R


class ProgressHUD : Dialog {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    override fun onWindowFocusChanged(hasFocus: Boolean) {
    /*    val imageView = findViewById<View>(R.id.spinnerImageView) as ImageView
        val spinner = imageView.background as AnimationDrawable
        spinner.start()*/
    }

    fun setMessage(message: CharSequence?) {
        if (message != null && message.length > 0) {
            findViewById<View>(R.id.message).visibility = View.VISIBLE
            val txt = findViewById<View>(R.id.message) as TextView
            txt.text = message
            txt.invalidate()
        }
    }

    companion object {

        fun create(context: Context,
            message: CharSequence?, indeterminate: Boolean, cancelable: Boolean, show: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
        ): ProgressHUD {
            val dialog = ProgressHUD(context, R.style.ProgressHUD)
            dialog.setTitle("")
            dialog.setContentView(R.layout.layout_progress_hud)

        /*    if (message == null || message.isEmpty()) {
                dialog.findViewById<View>(R.id.message).visibility = View.GONE
            } else {
                val txt = dialog.findViewById<View>(R.id.message) as TextView
                txt.text = message
            }*/

            dialog.setCancelable(cancelable)
            dialog.setOnCancelListener(cancelListener)
            dialog.window!!.attributes.gravity = Gravity.CENTER
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.2f
            dialog.window!!.attributes = lp
            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            if (show)
                dialog.show()
            return dialog
            
        }
    }
}