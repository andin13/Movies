package com.example.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movies.data.entities.Movie
import com.example.movies.data.services.MoviePageSource
import com.example.movies.data.services.NetworkService
import com.example.movies.ui.movies.MoviesFragment
import com.example.movies.ui.movies.MoviesViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
    fun inject(fragment: MoviesFragment)
}

@Module
object AppModule {


    @Provides
    fun providerMoviesViewModelFactory(networkService: NetworkService): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MoviesViewModel(networkService) as T
            }
        }
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/movies/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor{chain ->
                chain.proceed(
                    chain.request().run {
                        val url = url.newBuilder().setQueryParameter("api-key", "hlTtj7O2ePvq9Dskvi0XL3zXES4fbIkR").build()
                        newBuilder().url(url).build()
                    }
                )
            }
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}