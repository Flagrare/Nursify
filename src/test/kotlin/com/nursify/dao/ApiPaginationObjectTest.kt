package com.nursify.dao

data class ApiPaginationObjectTest(
    val sort: List<String>,
    val pageNumber: Int,
    val pageSize: Int
)