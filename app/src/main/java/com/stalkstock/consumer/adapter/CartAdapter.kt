package com.stalkstock.consumer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.fragment.CartFragment
import com.stalkstock.consumer.model.ModelCartData
import kotlinx.android.synthetic.main.row_cart.view.*

class CartAdapter(
    var context: CartFragment,
    var mContext: Context,
    var arrayList: ArrayList<ModelCartData.Body.CartData>
) :
    RecyclerView.Adapter<CartAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_cart, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var currentQty = arrayList[position].quantity
        holder.itemView.name_text.setText(arrayList[position].product.name)
        holder.itemView.count.setText(arrayList[position].quantity.toString())
        holder.itemView.tvPrice.setText("$" + arrayList[position].product.mrp.toString())
        holder.itemView.minus.setOnClickListener {
            val i = currentQty - 1
            context.addToCartAPI(arrayList[position].productId.toString(), i.toString())
        }

      /*  if(arrayList[position].product.productType==1){
            holder.itemView.img1.setImageResource(R.drawable.red_dot)
        }else{
            holder.itemView.img1.setImageResource(R.drawable.green_dot)
        }*/
        //img1
        holder.itemView.plus.setOnClickListener {
            val i = currentQty + 1
            context.addToCartAPI(arrayList[position].productId.toString(), i.toString())
/*
            val intent = Intent(context, AddcartdetailsActivity::class.java)
            context.startActivity(intent)
*/
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
