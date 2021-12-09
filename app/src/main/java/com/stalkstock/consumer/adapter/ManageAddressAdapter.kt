package com.stalkstock.consumer.adapter

import android.app.Dialog
import android.content.Intent
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.consumer.activities.EditAddressDetail2Activity
import com.stalkstock.consumer.model.ModelUserAddressList
import kotlinx.android.synthetic.main.row_cart.view.*
import kotlinx.android.synthetic.main.row_manageaddress.view.*
import kotlinx.android.synthetic.main.row_manageaddress.view.count
import java.util.*

class ManageAddressAdapter(var context: ManageAddress, var body: List<ModelUserAddressList.Body>,var manageAddress:ManageAddress) :
    RecyclerView.Adapter<ManageAddressAdapter.RecyclerViewHolder>() {

    var selectedpos = -1
    lateinit var selectedAdd: CheckBox

    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var delete: RelativeLayout = view.findViewById(R.id.delete)
        var edit: RelativeLayout = view.findViewById(R.id.edit)
        var textType: TextView = view.findViewById(R.id.text)
        var location: TextView = view.findViewById(R.id.location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_manageaddress, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val cbDefault: CheckBox = holder.itemView.cbDefault
        val get = body[position]
        holder.itemView.location.text= body[position].geoLocation
        when (get.type) {
            "1" -> {
                holder.textType.text = "Home"
                if(get.isDefault=="1") {
                    holder.itemView.count.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.itemView.count1.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.textType.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.location.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.itemView.imgAddress.setImageResource(R.drawable.home_1)
                    holder.itemView.edit.setBackgroundResource(R.drawable.background_box_new)
                    holder.itemView.delete.setBackgroundResource(R.drawable.background_box_new)
                }else{
                    holder.itemView.count.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.edit.setBackgroundResource(R.drawable.background_box_black)
                    holder.itemView.count1.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.delete.setBackgroundResource(R.drawable.background_box_black)
                    holder.textType.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.location.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.imgAddress.setImageResource(R.drawable.home_black)
                }
                //holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.home_1, 0, 0, 0);
            }
            "2" -> {
                holder.textType.text = "Work"
                if(get.isDefault=="0") {
                    holder.itemView.edit.setBackgroundResource(R.drawable.background_box_black)
                    holder.itemView.delete.setBackgroundResource(R.drawable.background_box_black)
                    holder.itemView.count.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.count1.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.imgAddress.setImageResource(R.drawable.work_black)
                    holder.textType.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.location.setTextColor(context.resources.getColor(R.color.black_color))
                }else{
                    holder.itemView.edit.setBackgroundResource(R.drawable.background_box_new)
                    holder.itemView.count.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.itemView.delete.setBackgroundResource(R.drawable.background_box_new)
                    holder.itemView.count1.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.itemView.imgAddress.setImageResource(R.drawable.work_1)
                    holder.textType.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.location.setTextColor(context.resources.getColor(R.color.green_colour))
                }
            }
            else -> {
                holder.textType.text = "Other"
                if(get.isDefault=="1") {
                    holder.itemView.edit.setBackgroundResource(R.drawable.background_box_new)
                    holder.itemView.delete.setBackgroundResource(R.drawable.background_box_new)
                    holder.itemView.count.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.itemView.count1.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.textType.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.location.setTextColor(context.resources.getColor(R.color.green_colour))
                    holder.itemView.imgAddress.setImageResource(R.drawable.location_1)
                }else{
                    holder.itemView.edit.setBackgroundResource(R.drawable.background_box_black)
                    holder.itemView.delete.setBackgroundResource(R.drawable.background_box_black)
                    holder.itemView.count.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.count1.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.itemView.imgAddress.setImageResource(R.drawable.work_black)
                    holder.textType.setTextColor(context.resources.getColor(R.color.black_color))
                    holder.location.setTextColor(context.resources.getColor(R.color.black_color))
                }
            }
        }

        if(get.isDefault=="1"){
            cbDefault.isChecked = true
         //   cbDefault.text= "Default"
            cbDefault.setTextColor(context.resources.getColor(R.color.App))
        }else{
            cbDefault.isChecked = false
          //  cbDefault.text= "Set As Default"
            cbDefault.setTextColor(context.resources.getColor(R.color.dark_gray))

        }

        holder.delete.setOnClickListener { openStartInfoApp(body[position]) }
        holder.edit.setOnClickListener {
            val intent =Intent(context, EditAddressDetail2Activity::class.java)
            intent.putExtra("key","edit")
            intent.putExtra("body",body[position])
            context.startActivity(intent);
        }

        holder.itemView.setOnClickListener {
            if (selectedpos < 0) {
                selectedpos = position
                selectedAdd = cbDefault
                cbDefault.isChecked = true
             //   cbDefault.text= "Default"
                cbDefault.setTextColor(context.resources.getColor(R.color.App))

                manageAddress.setDefaultAddressApi(get.id.toString())
            } else {
                if (cbDefault.isChecked) {
                    selectedpos = -1
                    cbDefault.isChecked = false
                   // cbDefault.text= "Set As Default"
                    cbDefault.setTextColor(context.resources.getColor(R.color.dark_gray))

                    manageAddress.setDefaultAddressApi(get.id.toString())
                } else {
                    selectedAdd.isChecked = false
                    cbDefault.isChecked = true
                    //cbDefault.text= "Set As Default"
                    cbDefault.setTextColor(context.resources.getColor(R.color.dark_gray))

                    selectedpos = position
                    selectedAdd = cbDefault
                    manageAddress.setDefaultAddressApi(get.id.toString())
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return body.size
    }

    fun openStartInfoApp(get: ModelUserAddressList.Body) {
        val dialogSuccessful = Dialog(Objects.requireNonNull(context), R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.delete_successfully_alertaddress)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull(dialogSuccessful.window)
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)
        val btn_yes = dialogSuccessful.findViewById<Button>(R.id.btn_yes)
        val btn_no = dialogSuccessful.findViewById<Button>(R.id.btn_no)
        btn_yes.setOnClickListener {
            context.deleteAddressAPI(get)
            dialogSuccessful.dismiss()
        }
        btn_no.setOnClickListener { dialogSuccessful.dismiss() }
        dialogSuccessful.show()
    }

    init {
        this.context = context
        inflater = LayoutInflater.from(context)
    }
}