package cn.testrunner.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.testrunner.weather.db.DBManager

class MoreActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bgTv: TextView
    private lateinit var cacheTv: TextView
    private lateinit var versionTv: TextView
    private lateinit var shareTv: TextView
    private lateinit var exbgRg: RadioGroup
    private lateinit var backIv: ImageView
    private lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        bgTv = findViewById(R.id.more_tv_exchargebg);
        cacheTv = findViewById(R.id.more_tv_cache);
        versionTv = findViewById(R.id.more_tv_version);
        shareTv = findViewById(R.id.more_tv_share);
        exbgRg = findViewById(R.id.more_rg);
        backIv = findViewById(R.id.more_iv_back);

        //设置点击监听事件
        bgTv.setOnClickListener(this)
        cacheTv.setOnClickListener(this)
        shareTv.setOnClickListener(this)
        backIv.setOnClickListener(this)

        pref = getSharedPreferences("bg_pref", MODE_PRIVATE)

        val versionName: String = getVersionName()
        versionTv.text = "当前版本:  V $versionName"

        setRGListener()


    }

    private fun setRGListener() {
        //设置改变背景图片的单选按钮的监听
        exbgRg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                //获取目前的默认壁纸
                val bg = pref.getInt("bg", 0)
                val editor = pref.edit()
                val intent = Intent(this@MoreActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                when (checkedId) {
                    R.id.more_rb_green -> {
                        if (bg == 0) {
                            Toast.makeText(this@MoreActivity, "您选择的背景为当前背景,无需改变!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg", 0)
                        editor.apply()
                    }
                    R.id.more_rb_pink -> {
                        if (bg == 1) {
                            Toast.makeText(this@MoreActivity, "您选择的背景为当前背景,无需改变!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg", 1)
                        editor.apply()
                    }
                    R.id.more_rb_blue -> {
                        if (bg == 2) {
                            Toast.makeText(this@MoreActivity, "您选择的背景为当前背景,无需改变!", Toast.LENGTH_SHORT).show()
                            return
                        }
                        editor.putInt("bg", 2)
                        editor.apply()
                    }
                }
                startActivity(intent)
            }
        })
    }

    private fun getVersionName(): String {
        /**
         * 获取应用的版本名称
         */

        val manager = packageManager
        var versionName: String? = null
        try {
            val packageInfo = manager.getPackageInfo(packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionName.toString()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.more_tv_exchargebg -> {
                if (exbgRg.visibility == View.VISIBLE) {
                    exbgRg.visibility = View.GONE;
                } else {
                    exbgRg.visibility = View.VISIBLE;
                }
            }

            R.id.more_tv_cache -> {
                clearCache();
            }
            R.id.more_tv_share -> {
                shareSoftwareMsg("微天气App是一款超萌超可爱的天气预报软件,画面简约,播报天气情况精准,快来下载吧!");
            }
            R.id.more_iv_back -> {
                finish()
            }
        }
    }

    private fun shareSoftwareMsg(s: String) {
        /*分享软件的函数*/
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT, s)
        startActivity(Intent.createChooser(intent, "微天气"))
    }

    private fun clearCache() {
        /*清除缓存的函数*/
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示信息").setMessage("确定要删除所有缓存吗?")
        builder.setPositiveButton("确定") { _, _ ->
            DBManager().deleteAllInfo()
            Toast.makeText(this@MoreActivity, "已清除全部缓存", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MoreActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }.setNegativeButton("取消", null)
        builder.create().show()
    }
}
