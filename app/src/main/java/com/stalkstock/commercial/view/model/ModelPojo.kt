package com.live.stalkstockcommercial.ui.models

sealed class ModelPojo {
    data class MyOrdersListModel(var image:Int,
                                 var brandName:String?,
                                 var cityname:String?,
                                 var countryname:String?,
                                 var description:String?,
                                 var date :String?,
                                 var time:String?,
                                 var price:String?,
                                 var status:String?)

    data class MessageListModel(var image:Int, var name:String?, var message:String?, var time:String?, var numberOFMessages:Int)


}