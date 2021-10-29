package com.stalkstock.utils.others

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.WindowManager
import com.stalkstock.R


class ProgressHUD(context: Context) : Dialog(context) {

    override fun onWindowFocusChanged(hasFocus: Boolean) {
    }

    companion object {
        fun create(
            context: Context,
            cancelable: Boolean,
            show: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
        ): ProgressHUD {
            val dialog = ProgressHUD(context)
            dialog.setTitle("")
            dialog.setContentView(R.layout.layout_progress_hud)
            dialog.setCancelable(cancelable)
            dialog.setOnCancelListener(cancelListener)
            dialog.window!!.attributes.gravity = Gravity.CENTER
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.2f
            dialog.window!!.attributes = lp
            dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
            if (show) dialog.show()
            return dialog
        }}}