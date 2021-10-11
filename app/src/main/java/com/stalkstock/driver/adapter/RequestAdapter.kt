package com.stalkstock.driver.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.driver.models.HistoryDataBody
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables

class RequestAdapter(var listRequest: MutableList<HistoryDataBody>) : RecyclerView.Adapter<RequestAdapter.AdsHolder>() {


    lateinit var clickInterFace:OnClick

    override fun onBindViewHolder(holder: AdsHolder, position: Int) {

        holder.tvOrder.text ="Order ID : ${listRequest[position].orderNo}"
        holder.tvAddress.text =listRequest[position].vendorDetail.shopAddress

        Log.e("onBindViewHolder","====${listRequest[position].orderStatus}===")
        holder.tv_status.apply {
            when (listRequest[position].orderStatus) {
                1 -> {
                    this.text = this.context.getString(R.string.in_progress)
                    this.setTextColor(this.context.getColorStateList(R.color.orange_colour))
                }
                2 -> {
                    this.text = this.context.getString(R.string.packed)
                    this.setTextColor(this.context.getColorStateList(R.color.dark_green_colour))
                }

                4 -> {
                    this.text = this.context.getString(R.string.completed)
                    this.setTextColor(this.context.getColorStateList(R.color.dark_green_colour))
                }

            }
        }


        holder.tvDateTime.text = AppUtils.changeDateFormat(listRequest[position].createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat3,
            GlobalVariables.DATEFORMAT.DateTimeFormat2)

        holder.tv_name.text =listRequest[position].vendorDetail.shopName

        Glide.with(holder.itemView.context).load(listRequest[position].vendorDetail.shopLogo).placeholder(holder.itemView.context.getDrawable(R.drawable.place_holder)).into(holder.iv_profile)

       // holder.iv_profile.text ="Order ID : ${listRequest[position].orderNo}"
        // "body":[{"id":57,"orderNo":"O16335839473069","driverId":88,
        // "createdAt":"2021-10-07T05:19:07.000Z","deliveryTime":"10:49:07",
        // "deliveryDate":"2021-10-07","orderStatus":1,"vendorId":78,
        // "customerId":66,"acceptedLat":"30.70464830","acceptedLong":"76.71787170",
        // "shippingCharges":"2.00","isDriverReview":0,"firstName":"Abh",
        // "lastName":"k","email":"anikaesash@mailinator.com","phone":"2615889823681",
        // "rating":"0.000000","review":"",
        // "image":"http://192.168.1.156:8800/uploads/user/9e2d437d-4caa-478b-b529-927556e8
        // 2b2c.jpg","userDeclinedOrder":null,"vendorDetail":{"id":14,
        // "shopName":"Sanju Baghla","shopLogo":"","buisnessPhone":"596568686","city":"India",
        // "state":"Punjab","country":"India","postalCode":"140306","geoLocation":"",
        // "shopAddress":"Mohali Airport Chowk","latitude":"30.64278250",
        // "longitude":"76.75319730","phone":"9495686683"},"orderAddress":{"id":51,
        // "orderId":57,"bidId":0,"userId":66,"latitude":"30.71209210",
        // "longitude":"76.69266010","geoLocation":"Vedantu innovation Pvt. Ltd, D-199,
        // Industrial Area, 8B, Sahibzada Ajit Singh Nagar, Punjab 160055, India",
        // "street_address":"Vedantu innovation Pvt. Ltd","address_line2":"",
        // "city":"Sahibzada Ajit Singh Nagar","state":"Punjab","zipcode":"160055",
        // "country":"India","special_instruction":"","type":"1","created":1633583948,
        // "updated":1633583948,"createdAt":"2021-10-05T09:49:14.000Z",
        // "updatedAt":"2021-10-07T05:19:08.000Z"}}]}

        holder.itemView.apply {
            setOnClickListener {
                if(listRequest[position].orderStatus==4) clickInterFace.onClick(position)
                else clickInterFace.onClick(position)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_request, parent, false))
    }

    override fun getItemCount(): Int {
        return listRequest.size
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvOrder = view.findViewById<TextView>(R.id.tv_order)
        var tv_name = view.findViewById<TextView>(R.id.tv_name)
        var tvAddress = view.findViewById<TextView>(R.id.tvAddress)
        var tvDateTime = view.findViewById<TextView>(R.id.tvDateTime)
        var tv_status = view.findViewById<TextView>(R.id.tv_status)
        var iv_profile = view.findViewById<ImageView>(R.id.iv_profile)
    }

    fun onClickInterFace(click:OnClick)
    {
        this.clickInterFace = click
    }

    interface  OnClick{
        fun onClick(position: Int)
    }
}