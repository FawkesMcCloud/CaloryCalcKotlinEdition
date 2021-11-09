package com.example.calorycalckotlinedition.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    long ID;

    String name;
    String brand;
    float KCal;
    float Fats;
    float Carbs;
    float Fibers;
    float Sugars;
    float Proteins;


}
