package ru.dmitryobukhoff.utils;

import com.google.gson.Gson;

import java.io.PrintWriter;

public class Output {
    public static void print(PrintWriter printWriter, Object object){
        Gson gson = new Gson();
        printWriter.println(gson.toJson(object));
    }
}
