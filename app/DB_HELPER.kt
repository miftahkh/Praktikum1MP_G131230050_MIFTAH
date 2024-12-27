package com.example.androidimport android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class DB_HELPER(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, NULL, DATABASE_VERSION){

}