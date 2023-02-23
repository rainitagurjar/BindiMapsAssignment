package com.example.bindimaps.service

import com.example.bindimaps.model.MainUserSessionsItem
import com.example.bindimaps.model.MainVenuesItemItem
import retrofit2.Call
import retrofit2.http.GET

interface UserService {

    @GET(ApiConstant.SESSIONS)
    fun getSessions(): Call<List<MainUserSessionsItem>>

    @GET(ApiConstant.VENUES)
    fun getVenues(): Call<List<MainVenuesItemItem>>

}
