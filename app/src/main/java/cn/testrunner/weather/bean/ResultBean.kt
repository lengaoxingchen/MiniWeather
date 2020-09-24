package cn.testrunner.weather.bean

data class ResultBean(
    val currentCity: String,
    val pm25: String,
    val index: List<IndexBean>,
    val weather_data: List<WeatherDataBean>
)