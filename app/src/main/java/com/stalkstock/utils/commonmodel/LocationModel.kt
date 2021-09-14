package com.stalkstock.utils.commonmodel

import android.widget.AutoCompleteTextView
import android.widget.EditText
import java.io.Serializable
class LocationModel(var etCity :EditText? = null,
                            var etState :EditText? = null,
                            var etZipcode :EditText? = null,
                            var autoTvLocation :AutoCompleteTextView? = null) : Serializable
