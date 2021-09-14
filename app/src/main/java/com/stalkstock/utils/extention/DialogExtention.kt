package com.stalkstock.utils.extention

import android.app.Activity
import com.stalkstock.R
import com.tapadoo.alerter.Alerter

fun Activity.AlerterError(msg:String?)
{
    Alerter.create(this)
        .setText(msg!!)
        .setTitleAppearance(R.style.AlertTextAppearance_Title)
        .enableSwipeToDismiss()
        .setBackgroundColorRes(R.color.red_bid)
        .show()
}