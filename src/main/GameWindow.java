package main;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;
import main.utilities.Debug;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import javax.swing.*;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameWindow extends JFrame {

    public GameWindow() {

    }
}
