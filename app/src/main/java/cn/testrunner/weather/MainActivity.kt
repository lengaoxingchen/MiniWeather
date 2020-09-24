package cn.testrunner.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

//http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //增加点击事件
        main_iv_add.setOnClickListener(this)
        main_iv_more.setOnClickListener(this)
        main_layout_point.setOnClickListener(this)
        main_vp.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            main_iv_add -> {
            }
            main_iv_more -> {
            }
        }
    }
}
