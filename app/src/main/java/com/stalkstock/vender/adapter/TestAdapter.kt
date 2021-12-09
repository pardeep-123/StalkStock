package com.stalkstock.vender.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.loadImage
import com.stalkstock.vender.Model.ModelVendorProductList.Body.Product
import com.stalkstock.vender.fragment.MainHomeFragment
import com.stalkstock.vender.ui.ProductDetail
import kotlinx.android.synthetic.main.activity_home_two_fragment.*
import kotlinx.android.synthetic.main.list_home_two.view.*
import java.util.*
import kotlin.collections.ArrayList

class TestAdapter(var context: Context, var currentModel: ArrayList<Product>,var homeFragment: MainHomeFragment) :
    RecyclerView.Adapter<TestAdapter.RecyclerViewHolder>(),Filterable {
    var inflater: LayoutInflater
    var arrayList= ArrayList<Product>()
    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_home_two, parent, false)
        val viewHolder: RecyclerViewHolder = RecyclerViewHolder(v)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val product = arrayList[position]
        holder.itemView.homeitemimage.loadImage(product.productImage[0].image)
        holder.itemView.itemname.setText(product.name)
        holder.itemView.kg.setText(product.productMeasurement.name)
        holder.itemView.editname.setText(product.productCategory.name)
        holder.itemView.lb.setText(product.mrp+"/"+product.productMeasurement.name)
        holder.itemView.edititem.setOnClickListener {
            val intent = Intent(
                context,
                ProductDetail::class.java
            )
            intent.putExtra("product_id",product.id.toString())

            context.startActivity(intent)
        }

        if (product.availability==1)
        {
            holder.itemView.notavble.visibility = View.GONE
            holder.itemView.avble.visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.notavble.visibility = View.VISIBLE
            holder.itemView.avble.visibility = View.GONE
        }
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    arrayList = currentModel as ArrayList<Product>
                } else {
                    val resultList = ArrayList<Product>()
                    for (row in currentModel) {
                        if (row.name.toUpperCase(Locale.US).contains(
                                constraint.toString().toUpperCase(
                                    Locale.US
                                )
                            )

                            || row.name.toLowerCase(Locale.US).contains(
                                constraint.toString().toLowerCase(
                                    Locale.US
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    arrayList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = arrayList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                arrayList = results?.values as ArrayList<Product>

                if(arrayList.size>0){
                    homeFragment.tv_Notfound?.visibility= View.GONE
                    homeFragment.rvCategory?.visibility= View.VISIBLE
                }else{
                    homeFragment.tv_Notfound?.visibility= View.VISIBLE
                    homeFragment.rvCategory?.visibility= View.GONE
                }
                notifyDataSetChanged()
            }
        }
    }
}