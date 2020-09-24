package cn.testrunner.weather.bean

data class WeatherDataBean(
    val date:String,
    val dayPictureUrl: String,
    val nightPictureUrl: String,
    val weather: String,
    val wind: String,
    val temperature: String
)