package com.gawel.babyjudas.featureMain.data.datasource

import com.gawel.babyjudas.featureMain.data.models.SampleOP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WikiService {
    @GET("posts/{id}")
    suspend fun getKidsInfo(
        @Path("id") id: Int
    ) : Response<SampleOP>
}