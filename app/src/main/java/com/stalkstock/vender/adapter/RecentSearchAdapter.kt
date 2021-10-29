package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.fragment.SearchFragment
import com.stalkstock.consumer.model.RecentSearchListResponse
import kotlinx.android.synthetic.main.row_recent_search.view.*
import java.util.ArrayList

class RecentSearchAdapter(
    var context: Context?,
    var mRecentArrayList: ArrayList<RecentSearchListResponse.Body>,
    var searchFragment: SearchFragment
) :
    RecyclerView.Adapter<RecentSearchAdapter.ViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.row_recent_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvSearchTag.text = mRecentArrayList[position].search
        holder.itemView.ivCross.setOnClickListener { searchFragment.deleteRecentSearchAPI(mRecentArrayList[position].id.toString()) }
        holder.itemView.tvSearchTag.setOnClickListener { searchFragment.setSearchTag(mRecentArrayList[position].search) }
    }
    override fun getItemCount(): Int {
        return mRecentArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}