package cn.testrunner.weather.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "forecast.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        //创建表的操作
        val sql = "create table info(" +
                "_id integer primary key autoincrement," +
                "city varchar(20) unique not null," +
                "context text not null" +
                ")"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}