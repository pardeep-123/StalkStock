package com.stalkstock.common.extentions

import java.text.SimpleDateFormat
import java.util.*


    fun changeDateFormatExt(inputDateFormat: String, outputDateFormat: String, dateString: String):String
    {
        var date = ""
        try {
            var spf = SimpleDateFormat(inputDateFormat)
            val newDate: Date = spf.parse(dateString)
            spf = SimpleDateFormat(outputDateFormat)
            date = spf.format(newDate)
            println(date)
        }catch (e: Exception)
        {
            e.printStackTrace()
        }

        return date
    }