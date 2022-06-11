package by.baranovdev.bvdv11.mappers.WeatherForecast

import android.util.Log
import by.baranovdev.bvdv11.model.DayModel
import by.baranovdev.bvdv11.model.HourModel
import by.baranovdev.bvdv11.model.WeatherForecast
import by.baranovdev.bvdv11.model.response.WeatherForecastResponse

class WeatherForecastMapper {

    fun map(from :WeatherForecastResponse?, dayIndex:Int):WeatherForecast?{
        if(from!=null) {
            try {

                val currentDay = from.forecast.forecastday[dayIndex].day
                return WeatherForecast(
                    DayModel(
                        currentDay.avgtemp_c,
                        currentDay.daily_chance_of_rain,
                        currentDay.daily_will_it_rain,
                        currentDay.maxtemp_c,
                        currentDay.mintemp_c,
                        currentDay.condition.icon
                    ),
                    from.forecast.forecastday[0].hour.map {
                        HourModel(
                            it.chance_of_rain,
                            it.temp_c,
                            it.time
                        )
                    },
                    currentDay.condition.icon
                )
            } catch (e: Exception) {
                Log.e("MAPPER", e.message ?: "")
                return null
            }
        } else return null

    }

}