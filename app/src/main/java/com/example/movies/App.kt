package com.example.movies

import android.app.Application
import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class App: Application() {

    private var _remoteConfig: FirebaseRemoteConfig? = null
    val remoteConfig: FirebaseRemoteConfig get() = _remoteConfig!!
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        _INSTANCE = this
        appComponent = DaggerAppComponent.create()


        _remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = 5
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }



    companion object {
        private var _INSTANCE: App? = null
        val INSTANCE get() = _INSTANCE!!
    }

}

val Context.appComponent:AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }