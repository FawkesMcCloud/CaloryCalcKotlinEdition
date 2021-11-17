package com.example.calorycalckotlinedition.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product where name like '%'||:name||'%' ")
    fun getAll(name: String): Flow<List<Product>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(product: Product)
    @Delete
    fun delete(product: Product)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)
}