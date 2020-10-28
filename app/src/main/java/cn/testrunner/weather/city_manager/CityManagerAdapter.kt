package cn.testrunner.weather.city_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import cn.testrunner.weather.R
import cn.testrunner.weather.bean.WeatherBean
import cn.testrunner.weather.db.DatabaseBean
import com.google.gson.Gson

class CityManagerAdapter(private var context: Context, private var mDatas: List<DatabaseBean>) : BaseAdapter() {

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager, null)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder?
        }
        val bean: DatabaseBean = mDatas[position]

        if (holder != null) {
            holder.cityTv.text = bean.city
            val weatherBean = Gson().fromJson(bean.content, WeatherBean::class.java)

            //获取今日天气情况
            val dataBean = weatherBean.results[0].weather_data[0]
            holder.conTv.text = "天气:${dataBean.weather}"
            val split = dataBean.date.split("：")
            val todayTemp = split[1].replace(")", "")
            holder.currentTempTv.text = todayTemp
            holder.windTv.text = dataBean.wind
            holder.tempRangeTv.text = dataBean.temperature
        }



        return convertView
    }

    class ViewHolder(itemView: View) {
        var cityTv: TextView = itemView.findViewById(R.id.item_city_tv_city)
        var conTv: TextView = itemView.findViewById(R.id.item_city_tv_condition)
        var currentTempTv: TextView = itemView.findViewById(R.id.item_city_tv_temp)
        var windTv: TextView = itemView.findViewById(R.id.item_city_wind)
        var tempRangeTv: TextView = itemView.findViewById(R.id.item_city_temp_range)

    }
}