/**
 * ZenTime | TaskDetailsController.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.annotation.processing.Filer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TaskDetailsController extends EventHandler {
    // Declaration of class attributes
    private TaskDetailsGUI GUI;
    private SchedulerGUI scheduler;
    private int taskRow;
    private ActionListener startFinishButtonAction;

    /**
     * Constructor method for the TaskDetailsGUI's controller class
     * @author Hadi
     * @param taskDetailsGUI The TaskDetailsGUI object/instance
     * @param schedulerGUI The schedulerGUI's object/instance
     * @param row The row/index number of the selectedtask
     * @throws IOException An exception caused by file I/O
     */
    public TaskDetailsController(TaskDetailsGUI taskDetailsGUI, SchedulerGUI schedulerGUI, int row) throws IOException {
        // Initialization of attributes
        super();
        this.GUI = taskDetailsGUI;
        this.scheduler = schedulerGUI;
        this.taskRow = row;
        this.startFinishButtonAction = startFinish();
        this.closeButtonAction = close(GUI);
    }

    /**
     * Method which updates the duration.csv and productivity.csv files which store analytical data about task completion
     * @author Hadi
     * @param task A newly completed task
     * @throws IOException An exception caused by file I/O
     */
    private void updateAnalytics(Task task) throws IOException {
        // Retrieves local system date/time
        String currentDate = String.valueOf(new Date()).substring(4, 10);
        String dailyTasks = "";

        // Reads the daily tasks file and adds one to the number of tasks completed on the current day
        String line = "";
        BufferedReader dailyTasksReader = new BufferedReader(new FileReader("assets\\daily_tasks.csv"));
        while ((line = dailyTasksReader.readLine()) != null) {
            String[] dailyTaskDetails = line.split("~");
            if (dailyTaskDetails[0].equals(currentDate)) {
                dailyTaskDetails[1] = String.valueOf(Integer.parseInt(dailyTaskDetails[1]) + 1);
            }

            dailyTasks += dailyTaskDetails[0] + "~" + dailyTaskDetails[1] + "\n";
        }

        // Writes to the daily tasks file with updates/changes
        FileWriter dailyTasksWriter = new FileWriter("assets\\daily_tasks.csv");
        dailyTasksWriter.write(dailyTasks);
        dailyTasksWriter.flush();
        dailyTasksWriter.close();
        dailyTasksReader.close();

        // Updates the task duration file if the task is a timed task type
        if (task.getType().equals("Timed Task")) {
            // Reads the task duration file
            String taskDurations = "";
            line = "";
            BufferedReader taskDurationReader = new BufferedReader(new FileReader("assets\\duration.csv"));
            while ((line = taskDurationReader.readLine()) != null) {
                taskDurations += line + "\n";
            }

            // Writes to the task duration file with an added completion time measured in hours
            taskDurations += String.valueOf((task.getDateCompleted().getTime() - task.getDeadline().getTime()) / 3600000);
            FileWriter taskDurationsWriter = new FileWriter("assets\\duration.csv");
            taskDurationsWriter.write(taskDurations);
            taskDurationsWriter.flush();
            taskDurationsWriter.close();
        }
    }

    /**
     * Method which creates the ActionListener object/functionality for the Start/Finish button
     * @author Hadi
     * @return The ActionListener object for the Start/Finish button
     */
    private ActionListener startFinish() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieves the task from the system's list of tasks
                    importItems("task");
                    Task currentTask = getTask(taskRow);

                    // Updates the status of the task based on its current status
                    if (currentTask.getStatus().equals("Incomplete") && !currentTask.getType().equals("Event")) {
                        currentTask.setStatus("In Progress");
                    }
                    else {
                        currentTask.setStatus("Complete");
                        currentTask.setDateCompleted(new Date());
                        updateAnalytics(currentTask);
                    }

                    // Updates the system's list of tasks as well as the .csv file storing the list of tasks to reflect the change of status
                    updateTask(taskRow, currentTask);
                    updateItems("task");

                    // The scheduler GUI's table is cleared and updated with the list of tasks including the selected task's new status
                    scheduler.clearTasks();
                    for (Task task : getTasks()) {
                        scheduler.addRow(new Object[]{String.valueOf(task.getDeadline()).substring(4, 16), task.getName(), task.getStatus()});
                    }

                    // A popup dialog is created and shows the user the result of their action and closes the window
                    JOptionPane.showMessageDialog(null, "Successfully started/finished task!");
                    GUI.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which fills in the task's details into the GUI's components
     * @author Hadi
     */
    public void setTaskDetails() {
        try {
            // Retrieves the task from the system's list of tasks
            importItems("task");
            Task task = getTask(taskRow);

            // Sets the GUI components' values to the task's details
            GUI.setTaskName(task.getName());
            GUI.setTaskDescription(task.getDescription());
            GUI.setTimeAdded(task.getDateAdded());
            GUI.setDeadline(task.getDeadline());
            GUI.setTimeCompleted(String.valueOf(task.getDateCompleted()).equals("Thu Jan 01 23:59:00 EST 2122") ? "N/A" : String.valueOf(task.getDateCompleted()));
            GUI.setStatus(task.getStatus());
            GUI.setTaskType(task.getType());
            GUI.setNotifications(task.hasNotifications());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method which returns the Start/Finish button's ActionListener object
     * @author Hadi
     * @return The Start/Finish Button's ActionListener
     */
    public ActionListener getStartFinishButtonAction() {
        return startFinishButtonAction;
    }
}
