package com.example.manajemenuser.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by User on 26/02/2018.
 */
class DatabaseHelper(context: Context?) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql = ("CREATE TABLE " + TABLE_NAME
                + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME +
                " VARCHAR, " + COLUMN_EMAIL +
                " VARCHAR, " + COLUMN_DATE +
                " VARCHAR , " + COLUMN_STATUS +
                " TINYINT);")
        db.execSQL(sql)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        val sql = "DROP TABLE IF EXISTS Persons"
        db.execSQL(sql)
        onCreate(db)
    }

    fun addName(
        name: String?,
        email: String?,
        date: String?,
        status: Int
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_DATE, date)
        values.put(COLUMN_STATUS, status)
        db.insert(TABLE_NAME, null, values)
        db.close()
        return true
    }

    fun updateNameStatus(id: Int, status: Int, date: String?): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_STATUS, status)
        values.put(COLUMN_DATE, date)
        db.update(
            TABLE_NAME,
            values,
            "$COLUMN_ID=$id",
            null
        )
        db.close()
        return true
    }

    val names: Cursor
        get() {
            val db = this.readableDatabase
            val sql =
                "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID ASC;"
            return db.rawQuery(sql, null)
        }

    val unsyncedNames: Cursor
        get() {
            val db = this.readableDatabase
            val sql =
                "SELECT * FROM $TABLE_NAME WHERE $COLUMN_STATUS = 0;"
            return db.rawQuery(sql, null)
        }

    companion object {
        const val DB_NAME = "NamesDb"
        const val TABLE_NAME = "names"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_DATE = "date"
        const val COLUMN_STATUS = "status"
        const val DB_VERSION = 1
    }
}