package com.example.movies.data.services


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.ServerResponses
import com.example.movies.data.entities.Movie
import javax.inject.Inject


class MoviePageSource @Inject constructor(
    private val networkService: NetworkService,
    private val type:Boolean,
    private val order:Boolean,
    private val bySearch:String
    ) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?:1
        val offset = page.minus(1).times(20)

        val response = if(bySearch.isBlank()) networkService.getMovies(offset,type,order)
        else networkService.searchMovies(bySearch,type,order,offset)

        return when (response) {
            is ServerResponses.Error,
            is ServerResponses.Failure,
            is ServerResponses.TooManyRequests,
            is ServerResponses.AccessDenied -> LoadResult.Error(Exception("Ошибка загузки"))
            is ServerResponses.Successful<*> -> {
                @Suppress("UNCHECKED_CAST")
                val movies = response.data as List<Movie>
                val nextKey = if (movies.size == params.loadSize) page + 1 else null
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(movies, prevKey, nextKey)
            }
        }
    }

}