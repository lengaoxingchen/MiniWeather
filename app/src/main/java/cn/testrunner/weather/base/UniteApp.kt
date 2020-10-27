package cn.testrunner.weather.base

import android.app.Application
import cn.testrunner.weather.db.DBManager
import org.xutils.x

class UniteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        x.Ext.init(this)
        DBManager().initData(this)
    }
}