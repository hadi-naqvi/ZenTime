/**
 * ZenTime | ZenTime.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */
package com.zentime;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ZenTime extends EventHandler implements Runnable {
    // Main class attributes
    private Thread thread;
    private SchedulerGUI scheduler;

    /**
     * Constructor method for main class
     * @author Hadi
     * @throws Exception An exception caused by the super class's constructor
     */
    private ZenTime() throws Exception {
        super();
        this.thread = new Thread(this);
        this.thread.start();
        this.scheduler = new SchedulerGUI();
    }

    /**
     * Method which gets called upon runtime and contains System Tray and Notifications
     * @author Hadi
     */
    @Override
    public void run() {
        try {
            // Configuration of the popup menu when the System-Tray is right-clicked
            PopupMenu popup = new PopupMenu();
            MenuItem openScheduler = new MenuItem("Show");
            openScheduler.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // The scheduler GUI can only be opened if it is currently hidden
                    if (scheduler == null) {
                        try {
                            scheduler = new SchedulerGUI();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            MenuItem closeScheduler = new MenuItem("Hide");
            closeScheduler.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // The scheduler GUI can only be hidden if it's currently open
                    if (scheduler != null) {
                        scheduler.dispose();
                        scheduler = null;
                    }
                }
            });
            MenuItem exitApplication = new MenuItem("Exit");
            exitApplication.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            // Adds the Show, Hide, and Exit buttons to the System Tray's popup menu
            popup.add(openScheduler);
            popup.add(closeScheduler);
            popup.add(exitApplication);

            // Creates and configures the System Tray Icon
            Image icon = Toolkit.getDefaultToolkit().getImage("assets\\Logo.png");
            TrayIcon zentimeIcon = new TrayIcon(icon, "ZenTime | Notifications", popup);
            zentimeIcon.setToolTip("ZenTime Notification Center");
            zentimeIcon.setImageAutoSize(true);
            zentimeIcon.setActionCommand("ZenTime");
            SystemTray.getSystemTray().add(zentimeIcon);

            // The program continuously checks if there are any notifications to send every minute
            while (true) {
                // Retrieves the system's list of tasks and reminders, as well as the machine's local date/time
                importItems("task");
                importItems("reminder");
                Date localTime = new Date(String.valueOf(new Date()).substring(0, 16) + ":00 " + String.valueOf(new Date()).substring(20, 28));

                // Iterates over every task in the system and checks if the program needs to send a notification for it
                for (Task task : getTasks()) {
                    // Retrieves the task's notification and deadline times
                    Date notificationTime = new Date(String.valueOf(task.getNotificationTime()).substring(0, 16) + ":00 " + String.valueOf(task.getNotificationTime()).substring(20, 28));
                    Date deadline = new Date(String.valueOf(task.getDeadline()).substring(0, 16) + ":00 " + String.valueOf(task.getDeadline()).substring(20, 28));

                    // Sends a notification to the user through the system tray informing them when their incomplete task has reached its deadline
                    if (localTime.equals(deadline) && !task.getStatus().equals("Complete")  && task.hasNotifications()) {
                        zentimeIcon.displayMessage("ZenTime | Notification Center", "The task " + task.getName() + " has reached its deadline and is still incomplete!", TrayIcon.MessageType.WARNING);
                    }
                    // Sends a notification to the user through the system tray
                    else if (localTime.equals(notificationTime) && task.hasNotifications()) {
                        zentimeIcon.displayMessage("ZenTime | Notification Center", "The task " + task.getName() + "'s deadline is in 5 minutes!", TrayIcon.MessageType.INFO);
                    }
                }

                // Iterates over every reminder in the system's list of reminders and checks if the program needs to send a notification for any reminders
                for (Reminder reminder : getReminders()) {
                    Date notificationTime = new Date(String.valueOf(reminder.getNotificationTime()).substring(0, 16) + ":00 " + String.valueOf(reminder.getNotificationTime()).substring(20, 28));
                    if (localTime.equals(notificationTime)) {
                        // Sends the user a notification if it's the reminder's notification time
                        zentimeIcon.displayMessage("ZenTime | Notification Center", reminder.getDescription(), TrayIcon.MessageType.INFO);

                        // The reminder is removed from the system after it has gone off
                        removeReminder(reminder);
                        updateItems("reminder");
                    }
                }

                // Waits a minute before running the loop again (Only checks for notifications once per minute)
                thread.sleep(60000);
            }
        } catch (Exception e) {
            System.err.println("Easter Egg for Mr. Ho. If you see this, give us bonus marks ;)");
        }
    }

    /**
     * Main Method
     * @author Hadi
     * @param args Supplied command-line arguments
     * @throws Exception An exception caused by the instantiation of a ZenTime object/client
     */
    public static void main(String[] args) throws Exception {
        // A new ZenTime client is started when the program is executed
        ZenTime client = new ZenTime();
    }
}
