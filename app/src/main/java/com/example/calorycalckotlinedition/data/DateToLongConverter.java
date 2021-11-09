package com.example.calorycalckotlinedition.data;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToLongConverter {

    public static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @TypeConverter
    public static Date fromTimeStamp(Long val){
        return val == null ? null : new Date(val);
    }
    @TypeConverter
    public static Long dateToTimeStamp(Date date){
        if(date == null)
            return null;
        else{
            String s = formatter.format(date);
            try {
                return formatter.parse(s).getTime();
            } catch (ParseException e) {
                return null;
            }
        }

    }
}
