package sk.kosickaakademia.util;

public abstract class Log {
    public static void info(String message){
        System.out.println("[INFO] - " + message);
    }

    public static void error(String message){
        System.out.println("[ERROR] - " + message);
    }

    public static void ok(String message){
        System.out.println("[OK] - " + message);
    }

    public static void print(String message){
        System.out.println(message);
    }
}
