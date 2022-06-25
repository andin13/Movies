package com.example.movies.data.services


import com.example.movies.data.entities.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitNetworkService {
    @GET("reviews/search.json")
    fun getMovies(@Query("query") param: String, @Query("critics-pick") type: String, @Query("order") order: String, @Query("offset") offset: String): Call<ResponseBody>

    @GET("reviews/{type}.json")
    fun getReviewedMovies(@Path("type") path: String, @Query("offset") offset: String,  @Query("order") order: String): Call<ResponseBody>
}
