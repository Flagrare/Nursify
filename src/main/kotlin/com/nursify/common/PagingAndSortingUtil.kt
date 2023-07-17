package com.nursify.common

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

fun buildPageRequest(
    sort: List<String>?,
    pageNumber: Int,
    pageSize: Int
): PageRequest {
    val sortList = sort?.map {
        val (fieldName, direction) = it.trim().split(';')
        if ("asc".equals(direction, true)) {
            Sort.Order(Sort.Direction.ASC, fieldName)
        } else {
            Sort.Order(Sort.Direction.DESC, fieldName)
        }
    } ?: emptyList()
    return PageRequest.of(pageNumber, pageSize, Sort.by(sortList))
}
