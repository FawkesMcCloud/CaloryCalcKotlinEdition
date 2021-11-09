package com.example.calorycalckotlinedition.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [History::class,Product::class], version = 1, exportSchema = false)
@TypeConverters(DateToLongConverter::class)
abstract class CaloryDB : RoomDatabase() {

    abstract fun HistoryDao(): HistoryDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { _ -> scope.launch {
                    Log.d("RoomCallback","Callback init")
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CaloryDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CaloryDB {
            Log.d("CaloryDBInit","getDatabase called")
            return INSTANCE ?: synchronized(this) {
                Log.d("CaloryDB synced","room.build()")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CaloryDB::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}