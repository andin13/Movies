package com.example.movies.data.services

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movies.data.ServerResponses
import com.example.movies.data.entities.Movie
import kotlinx.coroutines.flow.Flow


import retrofit2.Retrofit
import retrofit2.awaitResponse
import javax.inject.Inject



class NetworkService @Inject constructor(retrofit: Retrofit) {

    private val retrofitService = retrofit.create(RetrofitNetworkService::class.java)

    suspend fun searchMovies(searchQuery: String, type: Boolean, order: Boolean, offset: Int): ServerResponses {
            try {

                val typeString = if (type) "Y" else "N"
                val orderString = if (order) "by-opening-date" else "by-publication-date"
                val response = retrofitService.getMovies(searchQuery, typeString, orderString, offset.toString()).awaitResponse()

                Log.d("TEST", "request: $searchQuery")

                return when (response.code()) {
                    in 200..299 -> response.body()?.movies?.let {
                            ServerResponses.Successful(it)
                        } ?: ServerResponses.Successful(listOf<Movie>())

                    401 -> ServerResponses.AccessDenied
                    429 -> ServerResponses.TooManyRequests
                    else -> ServerResponses.Error(response.body().toString())
                }
            } catch (e: Exception) {
                return ServerResponses.Failure
            }
    }

    suspend fun getMovies(offset: Int, type: Boolean, order: Boolean): ServerResponses {
        try {

            val typeString = if (type) "picks" else "all"
            val orderString = if (order) "by-opening-date" else "by-publication-date"

            val response = retrofitService.getReviewedMovies(typeString, offset.toString(), orderString).awaitResponse()

            return when (response.code()) {
                in 200..299 -> response.body()?.movies?.let {
                        ServerResponses.Successful(it)
                    } ?: ServerResponses.Successful(listOf<Movie>())

                401 -> ServerResponses.AccessDenied
                429 -> ServerResponses.TooManyRequests
                else -> ServerResponses.Error(response.body().toString())
            }
        } catch (e: Exception) {
            return ServerResponses.Failure
        }
    }

    fun getPagedMovies(type:Boolean, order:Boolean, bySearch:String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 20,
                initialLoadSize = 20,
                maxSize = 80,
                //jumpThreshold = 5
            ),
            pagingSourceFactory = { MoviePageSource(this, type, order, bySearch) }
        ).flow
    }
}