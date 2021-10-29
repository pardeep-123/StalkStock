package com.stalkstock.commercial.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.stalkstock.R
import com.stalkstock.commercial.view.model.CategoryList

class CategoryCommercialAdapter (private val mContext: Activity, private val hint:String,private val listItems: ArrayList<CategoryList>):
    BaseAdapter() {
    override fun getCount(): Int {
        return listItems.size
    }

    override fun getItem(position: Int): Any {
        return listItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemTitle: String? = listItems[position].name
        val inflater: LayoutInflater = mContext.layoutInflater
        @SuppressLint("ViewHolder")
        val row: View = inflater.inflate(R.layout.spiner_layout_text, parent, false)
        val label = row.findViewById<TextView>(R.id.spinnerTarget)
        label.hint = hint
        label.text = itemTitle
        return row
    }
}