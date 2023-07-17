package com.nursify.dao

import clipboardhealth.challenge.nursify.utils.toCleanedStringList
import java.time.OffsetDateTime

data class ApiShiftQueryPayload(
    val facilityId: Long? = null,
    val searchFromPast: Boolean? = false,
    val sort: List<String>,
    val pageNumber: Int,
    val pageSize: Int,
)

fun ApiShiftQueryPayload.toMap(): MutableMap<String, String> {
    val map: MutableMap<String, String> = HashMap()
    map["facilityId"] = facilityId.let { it?.toString() ?: "" }
    map["searchFromPast"] = searchFromPast.toString()
    map["sort"] = sort.toCleanedStringList()
    map["pageNumber"] = pageNumber.toString()
    map["pageSize"] = pageSize.toString()

    return map
}

data class ApiShiftRangeRequestOffset(
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)

