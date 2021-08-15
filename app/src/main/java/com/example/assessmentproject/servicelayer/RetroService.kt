package com.example.assessmentproject.servicelayer

import com.example.assessmentproject.model.TemperListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {
    //https://temper.works/api/v3/shifts?filter[date]=2021-08-14
    //shifts?filter[date]=2021-08-14
    @GET("shifts")
    fun getTemperListFromApi(@Query("filter[date]") date: String): Observable<TemperListModel>
}