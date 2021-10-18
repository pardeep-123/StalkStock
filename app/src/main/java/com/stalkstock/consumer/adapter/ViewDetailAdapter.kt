package com.stalkstock.consumer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.stalkstock.R
import com.stalkstock.consumer.model.UserBannerModel
import com.stalkstock.utils.loadImage
import java.util.ArrayList

class ViewDetailAdapter(var mcontext: Context, var currentModel: ArrayList<UserBannerModel.Body>) : PagerAdapter() {
    var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return currentModel.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun destroyItem(container: View, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.row_viewpager, container, false)
        val img = view.findViewById<View>(R.id.img) as ImageView
        img.loadImage(currentModel[position].image)
        container.addView(view)
        return view
    }
}