package cn.testrunner.weather.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DBManager {
    lateinit var database: SQLiteDatabase

    /**
     * 初始化数据库信息
     */
    fun initData(context: Context) {
        val dbHelper = DBHelper(context)
        database = dbHelper.writableDatabase;

    }

    /**
     * 查找数据库当中城市列表
     */

    fun queryAllCityName(): MutableList<String> {
        val cursor = database.query("info", null, null, null, null, null, null)
        val cityList = ArrayList<String>()
        while (cursor.moveToNext()) {
            val city = cursor.getString(cursor.getColumnIndex("city"))
            cityList.add(city)
        }
        return cityList
    }

    /**
     * 根据城市名称,替换信息内容
     */
    fun updateInfoByCity(city: String, content: String): Int {
        val values = ContentValues().apply {
            put("content", content)
        }
        return database.update("info", values, "city=?", arrayOf(city))
    }


    /**
     * 新增一条城市记录
     */
    fun addCityInfo(city: String, content: String): Long {
        val values = ContentValues().apply {
            put("city", city)
            put("content", content)
        }
        return database.insert("info", null, values)
    }

    /**
     * 根据城市名,查询数据库当中的内容
     */
    fun queryInfoByCity(city: String): String? {
        val cursor = database.query("info", null, "city=?", arrayOf(city), null, null, null)
        if (cursor.count > 0) {
            cursor.moveToNext()
            return cursor.getString(cursor.getColumnIndex("content"))
        }
        return null
    }

    /**
     * 存储城市天气要求最多存储5个城市信息,超过5个城市就不能存储了
     * 获取目前已经存储的城市数量
     */
    fun getCityCount(): Int {
        val cursor = database.query("info", null, null, null, null, null, null)
        return cursor.count

    }

    /**
     * 查询数据库当中的全部信息
     */
    fun queryAllInfo(): List<DatabaseBean> {
        val cursor = database.query("info", null, null, null, null, null, null)
        val list = ArrayList<DatabaseBean>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("_id"))
            val city = cursor.getString(cursor.getColumnIndex("city"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val bean = DatabaseBean(id, city, content)
            list.add(bean)
        }
        return list
    }

    /**
     * 根据城市名称,删除这个城市在数据库当中的数据
     */
    fun deleteInfoByCity(city: String): Int {
        return database.delete("info", "city=?", arrayOf(city))
    }

    /**
     * 删除表当中的所有的数据信息
     */
    fun deleteAllInfo() {
        val sql = "delete from info"
        database.execSQL(sql)
    }
}