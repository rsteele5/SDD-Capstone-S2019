package main.utilities;


import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
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
            System.out.println(ANSI_RED + "  [Error] -> "+  message + ANSI_RESET);
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
            startLoggingGc();
        }else{
            System.out.println(ANSI_YELLOW + "[Warning] -> Logging Disabled" + ANSI_RESET);
        }
    }

    static public void startLoggingGc() {
        // http://www.programcreek.com/java-api-examples/index.php?class=javax.management.MBeanServerConnection&method=addNotificationListener
        // https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/GarbageCollectionNotificationInfo.html#GARBAGE_COLLECTION_NOTIFICATION
        for (GarbageCollectorMXBean gcMbean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ManagementFactory.getPlatformMBeanServer().
                        addNotificationListener(gcMbean.getObjectName(), listener, null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Hooks into the Garbage Collector and logs when he does his thing
    static private NotificationListener listener = new NotificationListener() {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            CompositeData cd = (CompositeData) notification.getUserData();
            GarbageCollectionNotificationInfo gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd);
            GcInfo gcInfo = gcNotificationInfo.getGcInfo();
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                if(DebugEnabler.GARBAGE_COLLECTION)
                System.out.println(ANSI_YELLOW + "     [GC] -> Garbage Collected! || Duration: " + gcInfo.getDuration() + "ms ||" + ANSI_RESET);
            }
        }
    };
}
