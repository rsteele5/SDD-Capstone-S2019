package utilities;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Debug {

    private static PrintWriter out;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[91m";
    private static final String ANSI_GREEN = "\u001B[92m";
    private static final String ANSI_YELLOW = "\u001B[93m";

    private static long startTime = System.currentTimeMillis();

    public static void log(boolean status, String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println("[Debug] -> " + message);
            out.println("[Debug] -> " + message + "\n");
        }
    }

    public static void success(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_GREEN + "[Success] -> " + message + ANSI_RESET);
            out.println("[Success] -> " + message + "\n");
        }
    }

    public static void  warning(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_YELLOW + "[Warning] -> "+  message + ANSI_RESET);
            out.println("[Warning] -> "+  message + "\n");
        }
    }

    public static void error(boolean status,String message) {
        if(DebugEnabler.LOGGING_ACTIVE && status){
            System.out.println(ANSI_RED + "[Error] -> "+  message + ANSI_RESET);
            out.println("[Error] -> "+  message + "\n");
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

    public static void checkForRefresh(long currentTime){
        //File IO will save every minute
        long refreshTime = 60000;
        if(currentTime > startTime + refreshTime){
            startTime = currentTime;
            warning(DebugEnabler.LOGGING_ACTIVE, "Log refresh");
            endLog();
            startLog();
        }
    }

    public static void startLog() {

        startTime = System.currentTimeMillis();
        String fileName = "logs/log" + startTime + ".txt";
        try {
            out = new PrintWriter(fileName);
            if(DebugEnabler.LOGGING_ACTIVE){
                out.println("[Success] -> Logging activated successfully");
                System.out.println(ANSI_GREEN + "[Success] -> Logging activated successfully" + ANSI_RESET);
            }else{
                out.println("[Warning] -> Logging Disabled");
                System.out.println(ANSI_YELLOW + "[Warning] -> Logging Disabled" + ANSI_RESET);
            }
        } catch (FileNotFoundException exception) {
            System.out.println(ANSI_RED + "[Error] -> Cannot open file: " + fileName + ANSI_RESET);
        }

    }

    public static void endLog() {
        out.close();
    }
}
