package com.example.englishwordxml2.db

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(application: Application) :
    SQLiteOpenHelper(application, MyDataBase.DATABASE_NAME, null, MyDataBase.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(MyDataBase.SQL_CREATE_TABLE_WORDS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(MyDataBase.SQL_DELETE_TABLE_WORDS)
        onCreate(db)
    }
}