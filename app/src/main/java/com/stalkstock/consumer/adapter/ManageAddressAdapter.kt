package com.stalkstock.consumer.adapter

import android.app.Dialog
import android.content.Intent
import android.view.*
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.consumer.activities.EditAddressDetail2Activity
import com.stalkstock.consumer.model.ModelUserAddressList
import java.util.*

class ManageAddressAdapter(var context: ManageAddress, var body: List<ModelUserAddressList.Body>) :
    RecyclerView.Adapter<ManageAddressAdapter.RecyclerViewHolder>() {
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

        val get = body[position]
        when (get.type) {
            "1" -> {
                holder.textType.text = "Home"
                holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.home_green_icon, 0, 0, 0);
            }
            "2" -> {
                holder.textType.text = "Work"
                holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.work_icon, 0, 0, 0);
            }
            else -> {
                holder.textType.text = "Other"
                holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location_icon_for_edit, 0, 0, 0);
            }
        }

        holder.location.text = get.address_line2

        holder.delete.setOnClickListener { openStartInfoApp(body[position]) }
        holder.edit.setOnClickListener {
            val intent =Intent(context, EditAddressDetail2Activity::class.java)
            intent.putExtra("key","edit")
            intent.putExtra("body",body[position])
            context.startActivity(intent);

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