package cn.testrunner.weather.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

open class BaseActivity : AppCompatActivity(), Callback.CommonCallback<String> {

    fun loadData(url: String) {
        val params = RequestParams(url)
        x.http().get(params, this)
    }

    override fun onSuccess(result: String) {
    }

    override fun onError(ex: Throwable, isOnCallback: Boolean) {
    }

    override fun onCancelled(cex: Callback.CancelledException) {
    }

    override fun onFinished() {
    }
}
