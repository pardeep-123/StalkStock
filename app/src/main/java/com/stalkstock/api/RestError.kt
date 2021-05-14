package com.stalkstock.api

import com.google.gson.annotations.SerializedName

class RestError
{
    @SerializedName("code")
    var code: Int? = null

    @SerializedName("message")
    var message: String? = null
}