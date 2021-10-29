package com.stalkstock.consumer

data class TermsData(
    var success: Boolean = false, // true
    var code: Int = 0, // 200
    var message: String = "", // Term and Condition fetch successfully.
    var body: TermsBody = TermsBody()
)

data class TermsBody(
    var id: Int = 0, // 2
    var accessor: String = "", // termsAndConditions
    var title: String = "", // Terms & Conditions
    var content: String = "", // <p><b><span style="font-size: 36px;">Terms And&nbsp; Conditions</span> </b></p><p><br></p><p>text goes here...12312dsadadgghghghghgdfgdg testy</p><p><br></p><p><br></p><p><br></p><p></p><p></p><p></p>
    var created: Int = 0, // 1591716283
    var updated: Int = 0, // 1593409469
    var createdAt: String = "", // 2020-03-23T15:32:39.000Z
    var updatedAt: String = "" // 2020-06-29T05:44:28.000Z
)