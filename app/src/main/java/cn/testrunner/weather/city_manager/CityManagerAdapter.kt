package cn.testrunner.weather.city_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import cn.testrunner.weather.R
import cn.testrunner.weather.db.DatabaseBean
import kotlinx.android.synthetic.main.item_city_manager.view.*

class CityManagerAdapter(context: Context, mDatas: List<DatabaseBean>) : BaseAdapter() {
    private lateinit var context: Context
    private lateinit var mDatas: List<DatabaseBean>

    init {
        this.context = context
        this.mDatas = mDatas
    }

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
        var holder: VIewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager, null)
            holder = VIewHolder(convertView)
            convertView.tag = holder
        }else{

        }

        return convertView
    }

    class VIewHolder(itemView: View) {
        private var cityTv: TextView = itemView.findViewById(R.id.item_city_tv_city)
        private var conTv: TextView = itemView.findViewById(R.id.item_city_tv_condition)
        private var currentTempTv: TextView = itemView.findViewById(R.id.item_city_tv_temp)
        private var windTv: TextView = itemView.findViewById(R.id.item_city_wind)
        private var tempRangeTv: TextView = itemView.findViewById(R.id.item_city_temp_range)

    }
}