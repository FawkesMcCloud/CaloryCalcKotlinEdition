package com.example.calorycalckotlinedition

import android.app.Application
import com.example.calorycalckotlinedition.repository.HistoryRepo
import com.example.calorycalckotlinedition.data.CaloryDB
import com.example.calorycalckotlinedition.repository.ProductRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DataBaseApp : Application(){

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CaloryDB.getDatabase(this,applicationScope)}
    val historyRepo by lazy { HistoryRepo(database.HistoryDao()) }
    val productRepo by lazy { ProductRepo(database.ProductDao()) }

}