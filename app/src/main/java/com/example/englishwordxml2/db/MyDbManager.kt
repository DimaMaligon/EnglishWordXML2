package com.example.englishwordxml2.db

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.englishwordxml2.data.EnglishWord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyDbManager @Inject constructor(application: Application) {
    private val myDbHelper = DbHelper(application)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        db = myDbHelper.writableDatabase
    }

    fun insertToWordsTable(letter: String, word: String, translate: String) {
        val valuesSet = ContentValues().apply {
            put(MyDataBase.LETTER_WORDS, letter)
            put(MyDataBase.WORDS_OF_LETTER, word)
            put(MyDataBase.TRANSLATE_OF_WORD, translate)
        }
        db?.insert(MyDataBase.TABLE_NAME_WORDS, null, valuesSet)
    }

    @SuppressLint("Range")
    fun checkWordsTable(): Boolean {
        var dataNull = false

        val cursor = db?.query(
            MyDataBase.TABLE_NAME_WORDS,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,   // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val countRow = cursor?.count
        if (countRow == null || countRow < 4) {
            dataNull = true
        }
        cursor?.close()
        return dataNull
    }

    @SuppressLint("Range")
    fun readWordsTable(letter: String?): ArrayList<EnglishWord> {
        val dataList = ArrayList<EnglishWord>()

        val cursor = db?.query(
            MyDataBase.TABLE_NAME_WORDS,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            "letter_words = '$letter'",   // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )
        with(cursor) {
            while (this?.moveToNext() == true) {
                val dataWord = getString(getColumnIndex(MyDataBase.WORDS_OF_LETTER))
                val dataTranslate = getString(getColumnIndex(MyDataBase.TRANSLATE_OF_WORD))
                dataList.add(EnglishWord(dataWord, dataTranslate, false))
            }
        }
        cursor?.close()
        return dataList
    }

    @SuppressLint("Range")
    fun readRandomWordsTable(): HashMap<String, String> {
        val dataMap = HashMap<String, String>()

        val cursor = db?.query(
            MyDataBase.TABLE_NAME_WORDS,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,   // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            "RANDOM() LIMIT 4"            // The sort order
        )
        with(cursor) {
            while (this?.moveToNext() == true) {
                val dataWord = getString(getColumnIndex(MyDataBase.WORDS_OF_LETTER))
                val dataTranslate = getString(getColumnIndex(MyDataBase.TRANSLATE_OF_WORD))
                dataMap[dataWord] = dataTranslate
            }
        }
        cursor?.close()
        return dataMap
    }
}