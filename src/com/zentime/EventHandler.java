/**
 * ZenTime | EventHandler.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class EventHandler {
    // Declaration of class attributes (List of tasks and reminders in system)
    private List<Task> tasks = new ArrayList<Task>();
    private List<Reminder> reminders = new ArrayList<Reminder>();
    protected ActionListener closeButtonAction;

    /**
     * Constructor method for event-handler/controller classes
     * @author Hadi
     * @throws IOException An exception caused by file I/O
     */
    protected EventHandler() throws IOException {
        // Imports all the tasks and reminders into the system
        importItems("task");
        importItems("reminder");
        updateItems("reminder");
    }

    /**
     * This method reads the task/reminder list stored in the program's tasks.csv and reminders.csv files and populates the List of tasks/reminders
     * @author Hadi
     * @param itemType The type of item being imported into the system (Task or Reminder)
     * @throws IOException An exception caused by file I/O
     */
    protected void importItems(String itemType) throws IOException {
        // Clears the current list of tasks/reminders to be replaced with an updated list of the tasks/reminders
        if (itemType.equals("task")) {
            tasks.clear();
        }
        else {
            reminders.clear();
        }

        // Initialization of BufferedReader object to read data stored in tasks.csv
        String line = "";
        BufferedReader itemReader = new BufferedReader(new FileReader("assets\\" + ((itemType.equals("task")) ? "tasks.csv" : "reminders.csv")));

        // Reads the file line by line (item by item) and stores data as task objects in the list of tasks
        while ((line = itemReader.readLine()) != null) {
            // Reads/Converts the item info into a String array
            String[] itemDetails = line.split("~");

            // Creates a new task object with the data read through the tasks.csv file and adds it to list of tasks
            if (itemType.equals("task")) {
                tasks.add(new Task(itemDetails[0], itemDetails[1], itemDetails[2], new Date(itemDetails[3]),
                        new Date(itemDetails[4]), new Date(itemDetails[5]), new Date(itemDetails[6]),
                        itemDetails[7].equalsIgnoreCase("Yes"), itemDetails[8]));
            }
            // Creates a new reminder object with the data read through the reminders.csv file and adds it to list of reminders
            // A reminder is only added to the list of reminders if its time has not yet passed
            else if (!(new Date().before(new Date(itemDetails[2])))) {
                reminders.add(new Reminder(itemDetails[0], itemDetails[1], new Date(itemDetails[2])));
            }
        }

        itemReader.close();
    }

    /**
     * This method writes to the program's tasks.csv & reminders.csv files and stores the most up-to-date version of the program's tasks/reminders list
     * @author Hadi
     * @throws IOException An exception caused by file I/O
     */
    protected void updateItems(String itemType) throws IOException {
        // Initialization of FileWriter object
        FileWriter itemWriter = new FileWriter("assets\\" + ((itemType.equals("task")) ? "tasks.csv" : "reminders.csv"));

        // Writes the list of tasks to the tasks.csv file
        if (itemType.equals("task")) {
            // Iterates through each task and appends each task to a new row of the file
            for (Task task : tasks) {
                itemWriter.append(task.getName());
                itemWriter.append('~');
                itemWriter.append(task.getDescription());
                itemWriter.append('~');
                itemWriter.append(task.getType());
                itemWriter.append('~');
                itemWriter.append(String.valueOf(task.getDateAdded()));
                itemWriter.append('~');
                itemWriter.append(String.valueOf(task.getDeadline()));
                itemWriter.append('~');
                itemWriter.append(String.valueOf(task.getDateCompleted()));
                itemWriter.append('~');
                itemWriter.append(String.valueOf(task.getNotificationTime()));
                itemWriter.append('~');
                itemWriter.append(task.hasNotifications() ? "Yes" : "No");
                itemWriter.append('~');
                itemWriter.append(task.getStatus());
                itemWriter.append("\n");
            }
        }
        // Writes the list of reminders to the reminders.csv file
        else {
            // Iterates through each reminder and appends each task to a new row of the file
            for (Reminder reminder : reminders) {
                itemWriter.append(reminder.getName());
                itemWriter.append('~');
                itemWriter.append(reminder.getDescription());
                itemWriter.append('~');
                itemWriter.append(String.valueOf(reminder.getNotificationTime()));
                itemWriter.append("\n");
            }
        }

        // Closes/destructs the FileWriter object
        itemWriter.flush();
        itemWriter.close();
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the close button
     * @author Hadi
     * @return The close button's ActionListener
     */
    protected ActionListener close(SmallGUI GUI) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.dispose();
            }
        };
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the close button
     * @author Hadi
     * @return The close button's ActionListener
     */
    protected ActionListener close(LargeGUI GUI) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.dispose();
            }
        };
    }

    /**
     * Method which adds a new task to the system's list of tasks
     * @author Hadi
     * @param task The task being added
     */
    protected void addNewTask(Task task) {
        tasks.add(task);
    }

    /**
     * Method which updates the details of a task at a specified index in the system's list of tasks
     * @author Hadi
     * @param taskNumber The index of the task
     * @param newTask The new task
     */
    protected void updateTask(int taskNumber, Task newTask) {
        tasks.set(taskNumber, newTask);
    }

    /**
     * Method which removes a task from the system's list of tasks
     * @author Hadi
     * @param task The task being removed
     */
    protected void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Method which adds a new reminder to the system's list of remionders
     * @author Hadi
     * @param reminder The reminder being added
     */
    protected void addNewReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    /**
     * Method which updates the details of a reminder at a specified index in the system
     * @author Hadi
     * @param reminderNumber The index of the reminder
     * @param newReminder The new reminder
     */
    protected void updateReminder(int reminderNumber, Reminder newReminder) {
        reminders.set(reminderNumber, newReminder);
    }

    /**
     * Method which removes a reminder from the system's list of reminders
     * @author Hadi
     * @param reminder The reminder being removed
     */
    protected void removeReminder(Reminder reminder) {
        reminders.remove(reminder);
    }

    /**
     * This method returns a list of all the task objects in the system
     * @author Hadi
     * @return The list of tasks
     * @throws IOException An exception caused by file I/O
     */
    protected List<Task> getTasks() throws IOException {
        importItems("task");
        return tasks;
    }

    /**
     * Method which retrieves a task at a specified index in the system's list of tasks
     * @author Hadi
     * @param taskNumber The index of the task
     * @return The task
     * @throws IOException An exception caused by file I/O
     */
    protected Task getTask(int taskNumber) throws IOException {
        importItems("task");
        return tasks.get(taskNumber);
    }

    /**
     * Method which retrieves the system's list of reminders
     * @author Hadi
     * @return The list of reminders
     * @throws IOException An exception caused by file I/O
     */
    protected List<Reminder> getReminders() throws IOException {
        importItems("reminder");
        return reminders;
    }

    /**
     * Method which retrieves a reminder at a specified index in the system's list of tasks
     * @author Hadi
     * @param reminderNumber The index of the reminder
     * @return The reminder
     * @throws IOException An exception caused by file I/O
     */
    protected Reminder getReminder(int reminderNumber) throws IOException {
        importItems("reminder");
        return reminders.get(reminderNumber);
    }

    /**
     * Method which returns the close button's ActionListener object
     * @author Hadi
     * @return The close button's ActionListener object
     */
    protected ActionListener getCloseButtonAction() {
        return closeButtonAction;
    }
}