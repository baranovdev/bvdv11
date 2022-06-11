package by.baranovdev.bvdv11.repositories

import by.baranovdev.bvdv11.mappers.WeatherForecast.WeatherForecastMapper
import by.baranovdev.bvdv11.model.WeatherForecast
import by.baranovdev.bvdv11.model.response.WeatherForecastResponse
import by.baranovdev.bvdv11.network.WeatherForecastServiceProvider

class WeatherForecastRepository {

    private val weatherForecastService= WeatherForecastServiceProvider.provideWeatherForecastService()
    private val weatherForecastMapper = WeatherForecastMapper()

    suspend fun loadWeatherForecast(dayIndex:Int): WeatherForecast? {
        val response = weatherForecastService.loadWeatherForecast()
        return if (response.isSuccessful) {
            weatherForecastMapper.map(response.body(), dayIndex)
        } else {
            throw Throwable(response.errorBody().toString())
        }
    }
}