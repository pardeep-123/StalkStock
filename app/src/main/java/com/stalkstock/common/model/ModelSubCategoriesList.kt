package com.stalkstock.common.model

data class ModelSubCategoriesList(
    val body: List<Body>,
    val code: Int, // 200
    val message: String, // Sub Category list.
    val success: Boolean // true
) {
    data class Body(
        val hierarchyLevel: Int, // 2
        val id: Int, // 49
        val name: String, // beans
        val parentId: Int, // 38
        val status: Int // 1
    )
}