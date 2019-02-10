package utilities;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Log {

    public static PrintWriter out;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[91m";
    public static final String ANSI_GREEN = "\u001B[92m";
    public static final String ANSI_YELLOW = "\u001B[93m";

    public static boolean loggingActive = false;
    public static boolean drawingActive = false;

    public static void log(String message) {
        if(loggingActive){
            System.out.println("[Log] -> " + message);
            out.println("[Log] -> " + message + "\n");
        }
    }

    public static void  logSuccess(String message) {
        if(loggingActive){
            System.out.println(ANSI_GREEN + "[Success] -> " + message + ANSI_RESET);
            out.println("[Success] -> " + message + "\n");
        }
    }

    public static void  logWarning(String message) {
        if(loggingActive){
            System.out.println(ANSI_YELLOW + "[Warning] -> "+  message + ANSI_RESET);
            out.println("[Warning] -> "+  message + "\n");
        }
    }

    public static void  logError(String message) {
        if(loggingActive){
            System.out.println(ANSI_RED + "[Error] -> "+  message + ANSI_RESET);
            out.println("[Error] -> "+  message + "\n");
        }
    }

    public static void drawImage(Graphics2D graphics, int x, int y, BufferedImage image) {
        if(drawingActive) {
            graphics.drawImage(image, x, y, null);
        }
    }

    public static void drawRect(Graphics2D graphics, Rectangle2D rect) {
        if(drawingActive) {
            graphics.drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
        }
    }

    public static void startLog() {
        String fileName = "log.txt";
        try {
            out = new PrintWriter(fileName);
            out.println("[Success] -> " + "Logging activated successfully");
            System.out.println(ANSI_GREEN + "[Success] -> " + "Logging activated successfully" + ANSI_RESET);
        } catch (FileNotFoundException exception) {
            System.out.println(ANSI_RED + "[Error] -> "+  "Cannot open file: " + fileName + ANSI_RESET);
        }

    }

    public static void endLog() {
        out.close();
    }
}
