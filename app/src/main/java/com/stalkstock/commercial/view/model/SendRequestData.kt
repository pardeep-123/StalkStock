package com.stalkstock.commercial.view.model

import com.google.gson.JsonArray
import org.json.JSONArray

data class SendRequestData(
    //{"addressId":42,"bidItem":[{"productId":30,"qty":50}]}
   var  addressId:Int,
    var  bidItem:JsonArray
)