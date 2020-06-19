package com.example.festivalapp.schedule;

public class DateConverter {
    public static String convert(String date) {
        String[] parts = date.split("-");
        String[] months = {"stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca", "lipca", "sierpnia", "września", "października", "listopada", "grudnia"};
        String month = months[Integer.parseInt(parts[1]) - 1];
        return parts[2] + " " + month + " " + parts[0];
    }
}
