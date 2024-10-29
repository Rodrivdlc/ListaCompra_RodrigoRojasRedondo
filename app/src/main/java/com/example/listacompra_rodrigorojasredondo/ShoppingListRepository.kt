package com.example.listacompra_rodrigorojasredondo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class ShoppingListRepository(context: Context) {
    private val dbHelper = ShoppingListDatabaseHelper(context)

    fun addItem(item: ShoppingItem) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ShoppingListDatabaseHelper.COLUMN_NAME, item.name)
            put(ShoppingListDatabaseHelper.COLUMN_QUANTITY, item.quantity)
            put(ShoppingListDatabaseHelper.COLUMN_PRICE, item.price) // Añadir price
        }
        db.insert(ShoppingListDatabaseHelper.TABLE_NAME, null, values)
    }

    fun removeItem(itemId: Long) {
        val db = dbHelper.writableDatabase
        db.delete(ShoppingListDatabaseHelper.TABLE_NAME, "${ShoppingListDatabaseHelper.COLUMN_ID} = ?", arrayOf(itemId.toString()))
    }

    fun getItems(): List<ShoppingItem> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            ShoppingListDatabaseHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val items = mutableListOf<ShoppingItem>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(ShoppingListDatabaseHelper.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(ShoppingListDatabaseHelper.COLUMN_NAME))
                val quantity = getInt(getColumnIndexOrThrow(ShoppingListDatabaseHelper.COLUMN_QUANTITY))
                val price = getDouble(getColumnIndexOrThrow(ShoppingListDatabaseHelper.COLUMN_PRICE)) // Añadir price
                items.add(ShoppingItem(id, name, quantity, price))
            }
        }
        cursor.close()
        return items
    }
}
