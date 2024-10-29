package com.example.listacompra_rodrigorojasredondo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ShoppingListDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_QUANTITY INTEGER, " +
                "$COLUMN_PRICE REAL)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_PRICE REAL DEFAULT 0.0")
        }
    }

    companion object {
        private const val DATABASE_NAME = "shopping_list.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "shopping_items"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_PRICE = "price"
    }
}
