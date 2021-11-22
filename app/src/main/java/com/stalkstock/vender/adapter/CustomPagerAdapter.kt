package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.vender.Model.ModelProductDetail

class CustomPagerAdapter(
    private val mContext: Context,
    private val imagesList: MutableList<ModelProductDetail.Body.ProductImage>
) : PagerAdapter() {
    var mLayoutInflater: LayoutInflater =
        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.item_pager, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.imageView) as ImageView

        Glide.with(mContext).load(imagesList[position].image).into(imageView)
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return imagesList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

}