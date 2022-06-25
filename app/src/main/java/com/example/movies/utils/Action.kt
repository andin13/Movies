package com.example.movies.utils

open class Action {
    var isFirstRun = true
    fun invoke(func: () -> Unit) {
        if (isFirstRun) {
            isFirstRun = false
            func()
        }
    }
}