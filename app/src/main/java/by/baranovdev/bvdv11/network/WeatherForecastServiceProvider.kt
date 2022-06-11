package by.baranovdev.bvdv11.network

import by.baranovdev.bvdv11.BuildConfig
import android.os.Build
import android.os.Debug
import by.baranovdev.bvdv11.model.WeatherForecast
import by.baranovdev.bvdv11.model.response.WeatherForecastResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherForecastServiceProvider{
    fun provideWeatherForecastService(): WeatherForecastService {

        val interceptor = HttpLoggingInterceptor().apply{
//            if (BuildConfig.DEBUG) {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .client(okHttpClient)
            .build()

        return retrofit.create(WeatherForecastService::class.java)
    }
}