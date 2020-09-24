package cn.testrunner.weather

import android.app.Application
import org.xutils.x

class UniteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        x.Ext.init(this)
    }
}