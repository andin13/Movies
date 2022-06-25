package com.example.movies.ui.movies

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.data.entities.Movie
import com.example.movies.data.services.NetworkService
import com.example.movies.utils.Action
import kotlinx.coroutines.flow.*


class MoviesViewModel(
    private val networkService: NetworkService
): ViewModel() {

    private val _type: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val type: StateFlow<Boolean> = _type

    private val _order: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val order: StateFlow<Boolean> = _order

    private val _bySearch: MutableStateFlow<String> = MutableStateFlow("")
    val bySearch: StateFlow<String> = _bySearch

    val movies: Flow<PagingData<Movie>> = combine(type, order, bySearch.debounce(500)) { type, order, bySearch ->
        Triple(type, order, bySearch)
    }.flatMapLatest { triple ->
        networkService.getPagedMovies(triple.first, triple.second, triple.third)
    }.cachedIn(viewModelScope)

    private val _viewAction: MutableLiveData<MoviesViewActions> = MutableLiveData()
    val viewAction: LiveData<MoviesViewActions> = _viewAction

    fun setType (t: Boolean) {
        _type.value = t
    }

    fun setOrder (o: Boolean) {
        _order.value = o
    }

    fun setBySearch (s: String) {
        if(s != bySearch.value) _bySearch.value = s
    }

    fun clickItem(movie: Movie) {
        _viewAction.value = MoviesViewActions.ToMovieScreen(movie)
    }
}

sealed class MoviesViewActions : Action() {
    class HideKeyboard : MoviesViewActions()
    class ShowToast(val message: String) : MoviesViewActions()
    class ToMovieScreen(val movie: Movie) : MoviesViewActions()
}