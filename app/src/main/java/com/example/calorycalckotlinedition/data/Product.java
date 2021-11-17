package com.example.calorycalckotlinedition.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"name","brand"},unique = true)})
public class Product {
    @PrimaryKey(autoGenerate = true)
    long ID = 0;

    String name;
    String brand;
    float KCal;
    float Fats;
    float Carbs;
    float Fibers;
    float Sugars;
    float Proteins;

    public Product(){
        name = "";
        brand = "";
        KCal = 0.0f;
        Fats = 0.0f;
        Carbs = 0.0f;
        Fibers = 0.0f;
        Sugars = 0.0f;
        Proteins = 0.0f;
    }

    @Ignore
    public Product(long ID, String name, String brand, float KCal, float proteins, float carbs, float sugars, float fibers, float fats) {
        this.ID = ID;
        this.name = name;
        this.brand = brand;
        this.KCal = KCal;
        Fats = fats;
        Carbs = carbs;
        Fibers = fibers;
        Sugars = sugars;
        Proteins = proteins;
    }
    @Ignore
    public Product(String name, String brand, float KCal, float fats, float carbs, float fibers, float sugars, float proteins) {
        this.name = name;
        this.brand = brand;
        this.KCal = KCal;
        Fats = fats;
        Carbs = carbs;
        Fibers = fibers;
        Sugars = sugars;
        Proteins = proteins;
    }
}
