package ru.dmitryobukhoff.validators;

public class Validator {
    public static boolean isValid(String p1, String p2, String p3){
        return (p1 != null) && (p2 != null) && (p3 != null) && (!p1.isEmpty()) && (!p2.isEmpty()) && (!p3.isEmpty());
    }

    public static boolean isUrlEmpty(String url){
        return (url == null) || (url.length() == 1);
    }

    public static boolean hasUrlValidCodes(String url){
        return (url.length() < 7);
    }

}
