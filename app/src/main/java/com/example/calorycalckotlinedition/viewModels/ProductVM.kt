package com.example.calorycalckotlinedition.viewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.*
import com.example.calorycalckotlinedition.repository.ProductRepo
import com.example.calorycalckotlinedition.util.getProductsFromCVS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProductVM(private val repo: ProductRepo) : ViewModel() {
        val products = repo.products.asLiveData()

        fun changeFilter(name:String){
            repo.searchNameQuery.value = name
        }

        fun import(uri: Uri, context: Context) = viewModelScope.launch(Dispatchers.IO) {
            val products = getProductsFromCVS(uri,context)
            products?.forEach{
                    product -> repo.insert(product)
            }
        }
}

class ProductVMFactory(private val repo: ProductRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductVM::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductVM(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }

}