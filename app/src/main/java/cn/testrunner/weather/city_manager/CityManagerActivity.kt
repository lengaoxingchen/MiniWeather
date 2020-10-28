package cn.testrunner.weather.city_manager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.testrunner.weather.R
import cn.testrunner.weather.db.DBManager
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.city_iv_add -> {
                val cityCount = DBManager().getCityCount()
                if (cityCount < 5) {
                    val intent = Intent(this, SearchCityActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "存储城市达到上限,请删除后再增加", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.city_iv_back -> {
                finish()
            }
            R.id.city_iv_delete -> {
                val intent = Intent(this, DeleteCityActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * 获取数据库当中真实数据源,添加到原有数据源当中,提示适配器更新
     */
    override fun onResume() {
        super.onResume()
        val list = DBManager().queryAllInfo()
        mDatas.clear()
        mDatas.addAll(list)
        adapter.notifyDataSetChanged()
    }
}
