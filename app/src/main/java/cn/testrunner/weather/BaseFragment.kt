package cn.testrunner.weather

import androidx.fragment.app.Fragment
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

/**
 * x utils加载网络数据的步骤
 * 1.声明整体模块
 * 2.执行网络请求操作
 */

open class BaseFragment : Fragment(), Callback.CommonCallback<String> {
    fun loadData(path: String) {
        val params = RequestParams()

        x.http().get(params, this)
    }

    //请求完成后，会回调的接口
    override fun onFinished() {

    }

    //获取数据成功时，会回调的接口
    override fun onSuccess(result: String?) {

    }

    //取消请求时，会调用的接口
    override fun onCancelled(cex: Callback.CancelledException?) {

    }

    //获取数据失败时，会调用的接口
    override fun onError(ex: Throwable?, isOnCallback: Boolean) {

    }
}