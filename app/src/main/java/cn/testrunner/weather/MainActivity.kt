package cn.testrunner.weather

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.testrunner.weather.city_manager.CityManagerActivity
import cn.testrunner.weather.db.DBManager
import kotlinx.android.synthetic.main.activity_main.*


//http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainVp: ViewPager
    private lateinit var outLayout: RelativeLayout
    private lateinit var addCityIv: ImageView
    private lateinit var moreIv: ImageView
    private lateinit var pointLayout: LinearLayout


    //ViewPager的数据源
    private val fragmentList = ArrayList<Fragment>()

    //表示选中城市的集合
    private var cityList = DBManager().queryAllCityName()

    //表示ViewPager的页数指示器集合
    private val imgList = ArrayList<ImageView>()


    lateinit var adapter: CityFragmentPagerAdapter
    lateinit var pref: SharedPreferences
    var bgNum: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        addCityIv = findViewById(R.id.main_iv_add);
        moreIv = findViewById(R.id.main_iv_more);
        pointLayout = findViewById(R.id.main_layout_point);
        mainVp = findViewById(R.id.main_vp);
        outLayout = findViewById(R.id.main_out_layout)

        exChangeBg()


        //增加点击事件
        main_iv_add.setOnClickListener(this)
        main_iv_more.setOnClickListener(this)

        if (cityList.isEmpty()) {
            cityList.add("北京")
        }

        /**
         * 处理搜索界面跳转结果
         */
        try {
            val intent = intent
            val city = intent.getStringExtra("city")
            if (!cityList.contains(city) && !TextUtils.isEmpty(city) && city != null) {
                cityList.add(city)
            }
        } catch (e: Exception) {
            Log.i("MainActivity", "搜索页面数据返回异常")
        }

        //初始化ViewPager页面的方法
        initPager()

        adapter = CityFragmentPagerAdapter(supportFragmentManager, fragmentList)
        mainVp.adapter = adapter

        //创建小圆点指示器
        initPoint()

        //设置最后一个城市信息
        mainVp.currentItem = fragmentList.size - 1

        //设置ViewPager页面监听
        setPagerListener()
    }

    private fun initPoint() {
        //创建小圆点ViewPager页面指示器的函数
        (0 until fragmentList.size).forEach { i ->
            val pIv = ImageView(this)
            pIv.setImageResource(R.mipmap.a1)
            pIv.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            val lp = pIv.layoutParams as LinearLayout.LayoutParams
            lp.setMargins(0, 0, 20, 0)
            imgList.add(pIv)
            pointLayout.addView(pIv)
        }
        imgList[imgList.size - 1].setImageResource(R.mipmap.a2)
    }


    private fun setPagerListener() {
        mainVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                imgList.forEach { it ->
                    it.setImageResource(R.mipmap.a1)
                }
                imgList[position].setImageResource(R.mipmap.a2)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun initPager() {
        /*
        创建Fragment对象添加到ViewPager数据源当中
         */
        cityList.forEach { it ->
            val cwFrag = CityWeatherFragment()
            val bundle = Bundle()
            bundle.putString("city", it)
            cwFrag.arguments = bundle
            fragmentList.add(cwFrag)
        }
    }


    /**
     * 换壁纸函数
     */
    private fun exChangeBg() {
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE)
        bgNum = pref.getInt("bg", 2)
        when (bgNum) {
            0 -> {
                outLayout.setBackgroundColor(R.mipmap.bg)
            }
            1 -> {
                outLayout.setBackgroundColor(R.mipmap.bg2)
            }
            2 -> {
                outLayout.setBackgroundColor(R.mipmap.bg3)
            }

        }
    }

    override fun onClick(p0: View) {
        val intent = Intent()
        when (p0) {
            main_iv_add -> {
                intent.setClass(this, CityManagerActivity::class.java)
            }
            main_iv_more -> {
                intent.setClass(this, MoreActivity::class.java)
            }
        }
        startActivity(intent)
    }


    override fun onRestart() {
        super.onRestart()

        //获取数据库中还剩下的城市集合
        val list = DBManager().queryAllCityName()
        if (list.size == 0) {
            list.add("北京")
        }
        cityList.clear()  //重新加载之前,清空原来的数据源
        cityList.addAll(list)

        //剩余城市也要创建对应的fragment页面
        fragmentList.clear()
        initPager()
        adapter.notifyDataSetChanged()

        //页面数量发生改变,指示器的数量也会发生变化,重新设置添加指示器
        imgList.clear()
        pointLayout.removeAllViews() //将布局中所有元素全部移除
        initPoint()
        mainVp.currentItem = fragmentList.size - 1

    }
}
