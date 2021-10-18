package com.stalkstock.advertiser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.model.BusinessAdsList

class PreviewPagerAdapter(var context: Context, var imglist: ArrayList<String>) : PagerAdapter() {
    override fun getCount(): Int {
        return imglist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.row_image_slider, null)
        val imagslideid = itemView.findViewById<ImageView>(R.id.imagslideid)
        Glide.with(context).load(imglist[position]).into(imagslideid)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}




