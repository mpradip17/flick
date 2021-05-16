package com.kotlintest.app.network

import com.kotlintest.app.model.ImageSearchModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("rest/")
    fun getImageSearch(@QueryMap data : HashMap<String,Any> ): Observable<ImageSearchModel>
}