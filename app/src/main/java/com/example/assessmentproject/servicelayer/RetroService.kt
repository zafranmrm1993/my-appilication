package com.example.assessmentproject.servicelayer

import com.example.assessmentproject.model.TemperListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {
    @GET("shifts")
    fun getTemperListFromApi(@Query("filter[date]") date: String): Observable<TemperListModel>
}