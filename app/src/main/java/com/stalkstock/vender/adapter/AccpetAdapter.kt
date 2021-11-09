package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.adapter.CartAdapter
import com.stalkstock.consumer.fragment.CartFragment
import com.stalkstock.consumer.model.ModelCartData
import com.stalkstock.vender.Model.VendorBiddingListResponse
import com.stalkstock.vender.ui.BidFragment
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlin.collections.ArrayList

class AccpetAdapter(
    var mContext: Context,
    var arrayList: ArrayList<VendorBiddingListResponse.BidData>
) :
    RecyclerView.Adapter<AccpetAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.bidprdouctaccpetlist, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        /* holder.itemView.name_text.setText(arrayList[position].product.name)
         holder.itemView.count.setText(arrayList[position].quantity.toString())
         holder.itemView.tvPrice.setText("$" + arrayList[position].product.mrp.toString())
         holder.itemView.minus.setOnClickListener {
             val i = currentQty - 1
             context.addToCartAPI(arrayList[position].productId.toString(), i.toString())
         }
         holder.itemView.plus.setOnClickListener {
             val i = currentQty + 1
             context.addToCartAPI(arrayList[position].productId.toString(), i.toString())
 *//*
            val intent = Intent(context, AddcartdetailsActivity::class.java)
            context.startActivity(intent)
*//*
        }*/

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
