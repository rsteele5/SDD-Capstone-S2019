package main.utilities;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {

    private static PrintWriter out;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[91m";
    private static final String ANSI_GREEN = "\u001B[92m";
    private static final String ANSI_YELLOW = "\u001B[93m";

    public static void log(boolean status, String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println("  [Debug] -> " + message);
        }
    }

    public static void success(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_GREEN + "[Success] -> " + message + ANSI_RESET);
        }
    }

    public static void  warning(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_YELLOW + "[Warning] -> "+  message + ANSI_RESET);
        }
    }

    public static void error(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_RED + "[Error] ->   "+  message + ANSI_RESET);
        }
    }

    public static void drawImage(boolean status, Graphics2D graphics, int x, int y, BufferedImage image) {
        if(DebugEnabler.DRAWING_ACTIVE && status) {
            graphics.drawImage(image, x, y, null);
        }
    }

    public static void drawRect(boolean status, Graphics2D graphics, Rectangle2D rect) {
        if(DebugEnabler.DRAWING_ACTIVE && status) {
            graphics.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
        }
    }

    public static void startLog() {
        if(DebugEnabler.LOGGING_ACTIVE){
            System.out.println(ANSI_GREEN + "[Success] -> Logging activated successfully" + ANSI_RESET);
        }else{
            System.out.println(ANSI_YELLOW + "[Warning] -> Logging Disabled" + ANSI_RESET);
        }
    }
}
