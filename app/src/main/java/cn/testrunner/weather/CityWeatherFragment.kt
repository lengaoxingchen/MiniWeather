package cn.testrunner.weather


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import cn.testrunner.weather.base.BaseFragment
import cn.testrunner.weather.bean.IndexBean
import cn.testrunner.weather.bean.WeatherBean
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_city_weather.*
import kotlinx.android.synthetic.main.item_main_center.*

/**
 * A simple [Fragment] subclass.
 */
class CityWeatherFragment : BaseFragment(), View.OnClickListener {

    private val url11 = "http://api.map.baidu.com/telematics/v3/weather?location="
    private val url12 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo"
    private lateinit var indexList: List<IndexBean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_city_weather, container, false)
        initView(view)
        //可以通过activity传值获取到当前fragment加载的哪个地方的天气情况
        val city = arguments?.getString("city")
        val url = url11 + city + url12

        //调用父类获取数据的方法
        loadData(url)
        return view
    }

    override fun onSuccess(result: String?) {
        //解析并展示数据
        parseShowData(result)
    }


    override fun onError(ex: Throwable?, isOnCallback: Boolean) {
        super.onError(ex, isOnCallback)
    }

    private fun parseShowData(result: String?) {
        //使用gson解析数据
        val weatherBean = Gson().fromJson(result, WeatherBean::class.java)
        val resultBean = weatherBean.results[0]

        indexList = resultBean.index
        //设置TextView
        frag_tv_date.text = weatherBean.date
        frag_tv_city.text = resultBean.currentCity
        //获取今日天气情况
        val weatherDataBean = resultBean.weather_data[0]
        frag_tv_wind.text = weatherDataBean.wind
        frag_tv_temp_range.text = weatherDataBean.temperature
        frag_tv_condition.text = weatherDataBean.weather

        //获取实时天气温度情况，需要处理字符串
        val split = weatherDataBean.date.split(":")
        val temp = split[1].replace(")", "")

        frag_tv_current_temp.text = temp

        //设置显示的天气情况图片
        Picasso.with(activity).load(weatherDataBean.dayPictureUrl).into(frag_iv_today)

        //获取未来三天的天气情况，加载到layout中
        val futureList = resultBean.weather_data.take(1)
        for (future in futureList) {
            val itemView = LayoutInflater.from(activity).inflate(R.layout.item_main_center, null)
            itemView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            frag_center_layout.addView(itemView)
            //获取对应位置的天气情况
            item_center_tv_date.text = future.date
            item_center_tv_con.text = future.weather
            item_center_tv_temp.text = future.temperature
            Picasso.with(activity).load(future.dayPictureUrl).into(item_center_iv)

        }
    }

    private fun initView(view: View) {
        //用于初始化操作
        frag_index_tv_dress.setOnClickListener(this)
        frag_index_tv_wash_car.setOnClickListener(this)
        frag_index_tv_cold.setOnClickListener(this)
        frag_index_tv_sport.setOnClickListener(this)
        frag_index_tv_rays.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        val builder = AlertDialog.Builder(activity)
        when (p0) {
            frag_index_tv_dress -> {
                builder.setTitle("穿衣指数")
                val indexBean = indexList[0]
                val msg = indexBean.ZS + "\n" + indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定", null)
            }
            frag_index_tv_wash_car -> {
                builder.setTitle("洗车指数")
                val indexBean = indexList[1]
                val msg = indexBean.ZS + "\n" + indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定", null)
            }
            frag_index_tv_cold -> {
                builder.setTitle("感冒指数")
                val indexBean = indexList[2]
                val msg = indexBean.ZS + "\n" + indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定", null)
            }
            frag_index_tv_sport -> {
                builder.setTitle("运动指数")
                val indexBean = indexList[3]
                val msg = indexBean.ZS + "\n" + indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定", null)
            }
            frag_index_tv_rays -> {
                builder.setTitle("紫外线指数")
                val indexBean = indexList[4]
                val msg = indexBean.ZS + "\n" + indexBean.desc
                builder.setMessage(msg)
                builder.setPositiveButton("确定", null)
            }
        }
        builder.create().show()
    }
}
