package com.example.calorycalckotlinedition.repository

import androidx.annotation.WorkerThread
import com.example.calorycalckotlinedition.data.History
import com.example.calorycalckotlinedition.data.HistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*

class HistoryRepo(private val historyDao: HistoryDao) {
    val records: Flow<List<History>> = historyDao.getAll()

    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun insert(record: History){
        historyDao.insert(record)
    }

    @WorkerThread
    suspend fun getByDate(date: Date): History {
        return withContext(Dispatchers.IO){
            historyDao.getByDate(date)
        }
    }

    @WorkerThread
    suspend fun deleteAll(){
        historyDao.deleteAll()
    }

    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun delete(history: History){
        historyDao.delete(history)
    }

    @WorkerThread
    suspend fun deleteAllExceptDate(date: Date){
        historyDao.deleteAllExceptDate(date)
    }

    @WorkerThread
    suspend fun update(record: History){
        historyDao.update(record)
    }
}