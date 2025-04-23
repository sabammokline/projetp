package com.example.projet.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateutil {

    // Method to format the date
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    // Method to get the current date as a string
    public static String getCurrentDate() {
        Date date = new Date();
        return formatDate(date);
    }
}
