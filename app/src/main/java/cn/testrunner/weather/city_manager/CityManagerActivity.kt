package cn.testrunner.weather.city_manager

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import cn.testrunner.weather.R
import cn.testrunner.weather.db.DatabaseBean


class CityManagerActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var addIv: ImageView
    private lateinit var backIv: ImageView
    private lateinit var deleteIv: ImageView
    private lateinit var cityLv: ListView

    //显示列表的数据源
    val mDatas = ArrayList<DatabaseBean>()

    private lateinit var adapter: CityManagerAdapter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager)
        addIv = findViewById(R.id.city_iv_add)
        backIv = findViewById(R.id.city_iv_back)
        deleteIv = findViewById(R.id.city_iv_delete)
        cityLv = findViewById(R.id.city_lv)

        //添加点击事件

        //添加点击事件
        addIv.setOnClickListener(this)
        backIv.setOnClickListener(this)
        deleteIv.setOnClickListener(this)
        //设置适配器
        //设置适配器
        adapter = CityManagerAdapter(this, mDatas)
        cityLv.adapter = adapter
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
