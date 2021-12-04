package com.example.calorycalckotlinedition.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface HistoryDao {
    @Query("Select * From history_table ORDER BY date DESC")
    fun getAll(): Flow<List<History>>

    @Query("Select * FROM history_table WHERE date LIKE '%' || :date || '%' LIMIT 1")
    fun getByDate(date: Date?): History

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: History)

    @Update
    fun update(record: History)

    @Delete
    suspend fun delete(record: History)

    @Query("Delete FROM history_table")
    suspend fun deleteAll()

    @Query("Delete from history_table where date NOT LIKE '%' || :date ||'%' ")
    suspend fun deleteAllExceptDate(date: Date?)
}