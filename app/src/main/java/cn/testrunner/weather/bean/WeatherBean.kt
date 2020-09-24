package cn.testrunner.weather.bean

data class WeatherBean(
    val error: Int, val status: String, val date: String, val results: List<ResultBean>
)
