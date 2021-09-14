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

class MangeaddressAdapter(var context: ManageAddress, var body: List<ModelUserAddressList.Body>) :
    RecyclerView.Adapter<MangeaddressAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var delete: RelativeLayout
        var edit: RelativeLayout
        var textType: TextView
        var location: TextView

        init {
            delete = view.findViewById(R.id.delete)
            edit = view.findViewById(R.id.edit)
            textType = view.findViewById(R.id.text)
            location = view.findViewById(R.id.location)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_manageaddress, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val get = body.get(position)
        if (get.type.equals("1"))
        {
            holder.textType.setText("Home")
            holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.home_green_icon, 0, 0, 0);
        }
        else if(get.type.equals("2")){
            holder.textType.setText("Work")
            holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.work_icon, 0, 0, 0);
        }
        else{
            holder.textType.setText("Other")
            holder.textType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location_icon_for_edit, 0, 0, 0);
        }

        holder.location.setText(get.address_line2)

        holder.delete.setOnClickListener { openStartInfoApp(body.get(position)) }
        holder.edit.setOnClickListener {
            var intent: Intent =Intent(context, EditAddressDetail2Activity::class.java)
            intent.putExtra("key","edit");
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