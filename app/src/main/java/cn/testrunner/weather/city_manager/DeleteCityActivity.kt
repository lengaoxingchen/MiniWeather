package cn.testrunner.weather.city_manager

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.testrunner.weather.MainActivity
import cn.testrunner.weather.R
import cn.testrunner.weather.db.DBManager
import java.util.ArrayList

class DeleteCityActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var errorIv: ImageView
    private lateinit var rightIv: ImageView
    private lateinit var deleteLv: ListView

    //ListView的数据源
    private val mDatas: MutableList<String> = DBManager().queryAllCityName()

    //表示存储了删除的城市信息
    private lateinit var deleteCities: MutableList<String>

    private lateinit var adapter: DeleteCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_city)

        errorIv = findViewById(R.id.delete_iv_error)
        rightIv = findViewById(R.id.delete_iv_right)
        deleteLv = findViewById(R.id.delete_lv)

        //设置点击监听事件
        errorIv.setOnClickListener(this)
        rightIv.setOnClickListener(this)

        //适配器的设置
        adapter = DeleteCityAdapter(this, mDatas, deleteCities)
        deleteLv.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.delete_iv_error -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改吗?")
                    .setPositiveButton("舍弃更改") { _, _ ->
                        finish() //关闭当前的activity
                    }
                builder.setNegativeButton("取消", null)
                builder.create().show()
            }

            R.id.delete_iv_right -> {
                for (i in 0 until deleteCities.size) {
                    val city = deleteCities[i]

                    //调用删除城市的函数
                    DBManager().deleteInfoByCity(city)
                }
                //删除成功返回上一级页面

                //如果页面的城市数量为0,则返回首页
                if (mDatas.size == 0) {
                    Intent(this, MainActivity::class.java)
                } else {
                    finish()
                }
            }
        }
    }

}
