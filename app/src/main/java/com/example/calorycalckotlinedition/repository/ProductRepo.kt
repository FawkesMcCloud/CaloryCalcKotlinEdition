package com.example.calorycalckotlinedition.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
import com.example.calorycalckotlinedition.data.Product
import com.example.calorycalckotlinedition.data.ProductDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class ProductRepo(private val productDao: ProductDao) {

    val searchNameQuery = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    private val productFlow = searchNameQuery.flatMapLatest {
        productDao.getAll(it)
    }

    val products = productFlow

    @WorkerThread
    suspend fun insert(product: Product){
        productDao.insert(product)
    }

    @WorkerThread
    suspend fun delete(product: Product){
        productDao.delete(product)
    }
}