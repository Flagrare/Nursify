package com.nursify.api

import com.nursify.api.model.ApiAvailableShifts
import com.nursify.api.model.ApiWorkerShiftRange

interface ShiftsApiService {

    /**
     * POST /shifts/{workerId} : Gets shifts from worker
     *
     * @param workerId  (required)
     * @param facilityId  (optional)
     * @param searchFromPast  (optional, default to false)
     * @param pageNumber  (optional, default to 0)
     * @param pageSize  (optional, default to 20)
     * @param sort According to the Spring Pageable format the field name must be followed by the sortDirection. Example: [ &#39;start;desc&#39;, &#39;end;asc&#39;, &#39;facility.id;desc ] (optional)
     * @param apiWorkerShiftRange  (optional)
     * @return Shift info (status code 200)
     *         or No shifts available (status code 204)
     * @see ShiftsApi#getWorkerShifts
     */
    fun getWorkerShifts(workerId: kotlin.Long, facilityId: kotlin.Long?, searchFromPast: kotlin.Boolean, pageNumber: kotlin.Int, pageSize: kotlin.Int, sort: kotlin.collections.List<kotlin.String>?, apiWorkerShiftRange: com.nursify.api.model.ApiWorkerShiftRange?): com.nursify.api.model.ApiAvailableShifts
}
