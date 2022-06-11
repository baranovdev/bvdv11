package by.baranovdev.bvdv11.network

import by.baranovdev.bvdv11.model.WeatherForecast
import by.baranovdev.bvdv11.model.response.WeatherForecastResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface WeatherForecastService {

    @Headers("X-CMC_PRO_API_KEY:b5705378be8b4fbda49211431221006")
    @GET("v1/forecast.json")
    suspend fun loadWeatherForecast(
        @Query("key") key:String = "b5705378be8b4fbda49211431221006",
        @Query("q") city:String = "Minsk",
        @Query("days") days:Int = 2,
        @Query("aqi") aqi:String = "no",
        @Query("alerts") alerts:String = "no",
    ): Response<WeatherForecastResponse>

}