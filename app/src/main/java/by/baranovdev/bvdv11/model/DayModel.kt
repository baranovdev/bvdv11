package by.baranovdev.bvdv11.model

data class DayModel(
    val avgtemp_c: Double,
    val daily_chance_of_rain: Int,
    val daily_will_it_rain: Int,
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val imageUrl:String
)
