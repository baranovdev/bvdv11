package by.baranovdev.bvdv11.presentation

import android.app.Application
import androidx.lifecycle.*
import by.baranovdev.bvdv11.model.WeatherForecast
import by.baranovdev.bvdv11.repositories.WeatherForecastRepository
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val weatherForecastRepository = WeatherForecastRepository()

    private val _weatherForecastLiveData : MutableLiveData<WeatherForecast?> = MutableLiveData()
    val weatherForecastLiveData:LiveData<WeatherForecast?> = _weatherForecastLiveData

    private val _isLoadingLiveData : MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingLiveData:LiveData<Boolean> = _isLoadingLiveData

    var forTomorrow = false
    var title = ""

    fun getData(){

        viewModelScope.launch {
            _isLoadingLiveData.value = true
            title = if(forTomorrow){
                _weatherForecastLiveData.postValue(weatherForecastRepository.loadWeatherForecast(1))
                _isLoadingLiveData.postValue(false)
                "Погода в Минске на завтра"
            } else {
                _weatherForecastLiveData.postValue(weatherForecastRepository.loadWeatherForecast(0))
                _isLoadingLiveData.postValue(false)
                "Погода в Минске на сегодня"
            }
        }
    }

    fun setTemperatureText(min :Double?, max : Double?):String?{
        if(min != null && max != null){
            return "${if(min.toInt()<0) "-" else "+"}${min.toInt()} / ${if(max.toInt()<0) "-" else "+"}${max.toInt()} °С"
        }
        else return ""

    }


}