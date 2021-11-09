package com.example.calorycalckotlinedition.viewModels

import androidx.lifecycle.*
import com.example.calorycalckotlinedition.repository.HistoryRepo
import com.example.calorycalckotlinedition.data.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import java.util.*

class HistoryVM(private val repo: HistoryRepo) : ViewModel() {
    val allRecords: LiveData<List<History>> = repo.records.asLiveData()
    var recordByDate: MutableLiveData<History> = MutableLiveData()


    fun setDateFilter(date: Date) = viewModelScope.launch{ recordByDate.value = repo.getByDate(date) }
    private suspend fun getRecordByDate(date: Date): History? {
        return withContext(Dispatchers.IO){
            repo.getByDate(date)
        }
    }


    fun insert(record: History) = viewModelScope.launch {
        repo.insert(record)
    }

    fun deleteAll() = viewModelScope.launch {
        repo.deleteAll()
    }

    fun delete(history: History) = viewModelScope.launch {
        repo.delete(history)
    }

    fun update(record: History) = viewModelScope.launch {
        repo.update(record)
    }


    fun deleteAllExceptDate(date: Date) = viewModelScope.launch {
        repo.deleteAllExceptDate(date)
    }



}

class HistoryVMFactory(private val repo: HistoryRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HistoryVM::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HistoryVM(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}