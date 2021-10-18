package com.stalkstock.advertiser.dialogClass

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.stalkstock.R
import kotlinx.android.synthetic.main.fragment_add_post.*
import java.util.*

class ToDatePickerFragment : DialogFragment() , DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var tvFromDates = requireActivity().findViewById<TextView>(R.id.tvFromDate)
        val getFromdate: String = tvFromDates.text.toString().trim()
        val getFrom = getFromdate.split("/").toTypedArray()
        val year: Int = getFrom[0].toInt()
        val month: Int = getFrom[1].toInt()
        val day: Int = getFrom[2].toInt()
        val c = Calendar.getInstance()
        c.set(year,month,day+1)

        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        datePickerDialog.datePicker.minDate = c.timeInMillis
        return datePickerDialog

    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var tvEndDate = requireActivity().findViewById<TextView>(R.id.c123)
        tvEndDate.text = "$year/$month/$dayOfMonth"

    }


}

