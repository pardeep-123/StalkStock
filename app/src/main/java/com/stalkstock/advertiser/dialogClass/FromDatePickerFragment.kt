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

class FromDatePickerFragment : DialogFragment() , DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        return datePickerDialog

    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
      var tvFromDates = requireActivity().findViewById<TextView>(R.id.tvFromDate)

        var months = month+1
       tvFromDates.text = "$year/$months/$dayOfMonth"


    }

}