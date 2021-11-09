package com.example.calorycalckotlinedition

import android.app.Application
import com.example.calorycalckotlinedition.repository.HistoryRepo
import com.example.calorycalckotlinedition.data.CaloryDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DataBaseApp : Application(){

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CaloryDB.getDatabase(this,applicationScope)}
    val repository by lazy { HistoryRepo(database.HistoryDao()) }

}