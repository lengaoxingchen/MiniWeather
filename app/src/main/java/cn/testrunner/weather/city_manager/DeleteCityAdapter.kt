package cn.testrunner.weather.city_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import cn.testrunner.weather.R


class DeleteCityAdapter(private var context: Context, private var mDatas: MutableList<String>) : BaseAdapter() {

    //要删除的城市集合
    private lateinit var deleteCities: MutableList<String>

    constructor(context: Context, mDatas: MutableList<String>, deleteCities: MutableList<String>) : this(context,mDatas) {
        this.context = context
        this.deleteCities = deleteCities
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
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager, null)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder?
        }
        val city = mDatas[position]
        if (holder != null) {
            holder.tv.text = city
            holder.iv.setOnClickListener {
                mDatas.remove(city)
                deleteCities.add(city)
                notifyDataSetChanged() //删除了提示适配器更新
            }
        }
        return convertView;
    }


    class ViewHolder(itemView: View) {
        var tv: TextView = itemView.findViewById(R.id.item_delete_tv)
        var iv: ImageView = itemView.findViewById(R.id.item_delete_iv)

    }
}