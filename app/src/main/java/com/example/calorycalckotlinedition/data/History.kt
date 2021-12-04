package com.example.calorycalckotlinedition.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history_table")
class History(
    @PrimaryKey(autoGenerate = true)
    val date: Date,
    var deltaKCal : Float = 0.0f,
    var deltaCarbs : Float = 0.0f,
    var deltaProteins : Float = 0.0f,
    var deltaFats : Float = 0.0f,
    var deltaSugars : Float = 0.0f,
    var deltaFibers : Float = 0.0f,

    var goalKcal: Float = 100.0f,
    var goalCarbs: Float = 100.0f,
    var goalProteins: Float = 100.0f,
    var goalFats: Float = 100.0f,
    var goalSugars: Float = 100.0f,
    var goalFibers: Float = 100.0f
)