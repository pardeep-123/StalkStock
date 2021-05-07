package com.tamam.net

import com.google.gson.annotations.SerializedName

class RestError
{
    @SerializedName("code")
    var code: Int? = null

    @SerializedName("msg")
    var message: String? = null
}