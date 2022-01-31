package com.stalkstock.utils.others
import android.content.Context
import android.view.animation.AnimationUtils
import com.stalkstock.R


class Anim(c: Context)  {
    val slide_in_bottom = AnimationUtils.loadAnimation(
        c,
        R.anim.slide_in_bottom
    )
}