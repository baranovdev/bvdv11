package by.baranovdev.bvdv11.model.response

data class Forecastday(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)