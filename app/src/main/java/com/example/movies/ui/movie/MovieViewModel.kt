package com.example.movies.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.data.entities.Movie


class MovieViewModel(
    val movie: Movie
): ViewModel() {

    private val _description: MutableLiveData<String> = MutableLiveData("")
    val description: LiveData<String> = _description

    private val _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _imageSrc: MutableLiveData<String> = MutableLiveData("")
    val imageSrc: LiveData<String> = _imageSrc

}