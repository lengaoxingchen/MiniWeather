package cn.testrunner.weather.city_manager

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import cn.testrunner.weather.MainActivity
import cn.testrunner.weather.R
import cn.testrunner.weather.base.BaseActivity
import cn.testrunner.weather.base.UrlConst
import cn.testrunner.weather.bean.WeatherBean
import com.google.gson.Gson

class SearchCityActivity : BaseActivity(), View.OnClickListener {

    private lateinit var searchEt: EditText
    private lateinit var submitIv: ImageView
    private lateinit var searchGv: GridView
    private val hotCities = listOf(
        "北京", "上海", "广州", "深圳", "珠海", "佛山", "南京", "苏州", "厦门", "长沙", "成都", "福州", "杭州", "武汉",
        "青岛", "西安", "太原", "沈阳", "重庆", "天津", "南宁"
    )

    private lateinit var city: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)


        searchEt = findViewById(R.id.search_et)
        submitIv = findViewById(R.id.search_iv_submit)
        searchGv = findViewById(R.id.search_gv)

        //设置监听事件
        submitIv.setOnClickListener(this)

        //设置适配器
        val adapter = ArrayAdapter<String>(this, R.layout.item_hotcity, hotCities)
        searchGv.adapter = adapter

        setListener()
    }

    private fun setListener() {
        searchGv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            city = hotCities[position]
            val url = UrlConst().url1 + city + UrlConst().url2
            loadData(url)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.search_iv_submit -> {
                city = searchEt.text.toString()
                if (!TextUtils.isEmpty(city)) {
                    //判断是否能找到这个城市
                    val url = UrlConst().url1 + city + UrlConst().url2
                    loadData(url)
                } else {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    override fun onSuccess(result: String) {
        val weatherBean = Gson().fromJson(result, WeatherBean::class.java)
        if (weatherBean.error == 0) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("city", city)
            startActivity(intent)
        } else {
            Toast.makeText(this, "暂时未收录此城市天气信息", Toast.LENGTH_SHORT).show()
        }
    }
}
