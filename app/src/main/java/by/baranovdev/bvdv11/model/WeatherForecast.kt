package by.baranovdev.bvdv11.model

data class WeatherForecast(
    val currentDay:DayModel,
    val listHours:List<HourModel>,
    val imageUrl:String
) {

}