package com.tusbus.mymvvm.common.network

import com.tusbus.mymvvm.base.BaseResp
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author songyaru
 * @date 2020/8/17
 */
interface ApiService {
    @FormUrlEncoded
    @POST("/web/Agreement/getUrl")
    suspend fun userIdentityStatus(@FieldMap map: MutableMap<String, String>): BaseResp<String>

    @GET("timeline.json")
    suspend fun timelineJson(): String
}