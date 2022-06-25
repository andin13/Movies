package com.example.movies.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movies.App
import com.example.movies.BuildConfig
import com.example.movies.utils.Action

class SplashViewModel : ViewModel() {

    private val _viewAction: MutableLiveData<SplashViewActions> = MutableLiveData()
    val viewAction: LiveData<SplashViewActions> = _viewAction

    fun entryPoint() {
        val remoteConfig = App.INSTANCE.remoteConfig

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                val recommendedUpdateVersion = remoteConfig.getLong("RecommendedUpdate")
                _viewAction.value = if (BuildConfig.VERSION_CODE <= recommendedUpdateVersion)
                    SplashViewActions.GoToUpdateScreen()
                else
                    SplashViewActions.GoToMoviesScreen()
            }
    }

}

sealed class SplashViewActions : Action() {
    class GoToUpdateScreen : SplashViewActions()
    class GoToMoviesScreen : SplashViewActions()
}