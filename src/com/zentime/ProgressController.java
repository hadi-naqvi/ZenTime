/**
 * ZenTime | ProgressController.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ProgressController extends EventHandler {
    // Declaration of class attributes
    private ProgressGrapher grapher;
    private ProgressGUI GUI;
    private ActionListener taskProgressButtonAction, completionStatsButtonAction,
            completionTimesButtonAction;

    /**
     * Constructor method for the ProgressGUI's controller class
     * @author Hadi
     * @param progressGUI The object/instance of the ProgressGUI
     * @throws IOException An exception caused by file I/O
     */
    public ProgressController(ProgressGUI progressGUI) throws IOException {
        super();
        this.grapher = new ProgressGrapher();
        this.GUI = progressGUI;
        this.taskProgressButtonAction = generateTaskProgressGraph();
        this.completionStatsButtonAction = generateCompletionStatsGraph();
        this.completionTimesButtonAction = generateCompletionTimesGraph();
        this.closeButtonAction = close(GUI);
    }

    /**
     * Method which returns the average early and late completion times for tasks
     * @author Hadi
     * @return The average early and late completion times measured in # of hours in a String array
     * @throws IOException An exception caused by file I/O
     */
    private String[] getAverageTimes() throws IOException {
        // Initialization of the average times
        String[] averageTimes = {"-0", "+0"};
        int averageEarlyTime = Integer.MAX_VALUE, averageLateTime = Integer.MIN_VALUE;

        // Reads the duration.csv file containing task completion times and calculates the average completion times
        String line = "";
        BufferedReader taskDurationReader = new BufferedReader(new FileReader("assets\\duration.csv"));
        while ((line = taskDurationReader.readLine()) != null) {
            // Initializes the first value of the completion times
            if (averageEarlyTime == Integer.MAX_VALUE && Integer.parseInt(line) < 0) {
                averageEarlyTime = Integer.parseInt(line);
                continue;
            }
            else if (averageLateTime == Integer.MIN_VALUE && Integer.parseInt(line) > 0) {
                averageLateTime = Integer.parseInt(line);
                continue;
            }

            // Calculates the average early or late completion time
            if (Integer.parseInt(line) < 0) {
                averageEarlyTime = (averageEarlyTime + Integer.parseInt(line)) / 2;
            }
            else {
                averageLateTime = (averageLateTime + Integer.parseInt(line)) / 2;
            }
        }

        // Returns the average completion times
        if (averageEarlyTime != Integer.MAX_VALUE) averageTimes[0] = String.valueOf(averageEarlyTime);
        if (averageLateTime != Integer.MIN_VALUE) averageTimes[1] = String.valueOf(averageLateTime);
        return averageTimes;
    }

    /**
     * Method which creates the ActionListener/functionality for the task progress button
     * @author Hadi
     * @return The task progress button's ActionListener
     */
    private ActionListener generateTaskProgressGraph() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Determines the number of complete, incomplete, and in progress tasks by iterating through the system's list of tasks
                    int completeTasks = 0, inProgressTasks = 0, incompleteTasks = 0;
                    for (Task task : getTasks()) {
                        if (task.getStatus().equals("Complete")) {
                            completeTasks++;
                        }
                        else if (task.getStatus().equals("In Progress")) {
                            inProgressTasks++;
                        }
                        else {
                            incompleteTasks++;
                        }
                    }

                    // Generates a chart displaying the current task progress
                    if (!(completeTasks == 0 && inProgressTasks == 0 && incompleteTasks == 0)) {
                        grapher.graphProgress(completeTasks, inProgressTasks, incompleteTasks);
                    }
                    else {
                        // If there are no tasks, the user is told there is not enough data to generate the chart
                        JOptionPane.showMessageDialog(null,"There is not enough data to generate this chart.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates the ActionListener/functionality for the Completion Statistics button
     * @author Hadi
     * @return The completion statistics button's ActionListener
     */
    private ActionListener generateCompletionStatsGraph() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Determines the number of tasks completed early and late
                    int early = 3, late = 5;
                    for (Task task : getTasks()) {
                        if (task.getStatus().equals("Complete")) {
                            if (task.getDateCompleted().before(task.getDeadline())) {
                                early++;
                            }
                            else {
                                late++;
                            }
                        }
                    }

                    // Generates a chart displaying the completion statistics
                    if (!(late == 0 && early == 0)) {
                        grapher.graphCompletionStatistics(early, late);
                    }
                    else {
                        // If there are no completed tasks, the user is told there is not enough data to generate the chart
                        JOptionPane.showMessageDialog(null,"There is not enough data to generate this chart.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates the ActionListener/functionality for the Completion times button
     * @author Hadi
     * @return The completion times button's ActionListener
     */
    private ActionListener generateCompletionTimesGraph() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Initializes the list of deadlines, estimated task completion times, and average early/late completion times
                    ArrayList<String> deadlines = new ArrayList<String>();
                    ArrayList<String> estimatedCompletionTimes = new ArrayList<String>();
                    String[] averageTimes = getAverageTimes();

                    // Iterates over every task and adds its deadline and estimated completion time to the arraylists above
                    for (Task task : getTasks()) {
                        if (!(task.getStatus().equals("Complete"))) {
                            // Adds the task's deadline to the arraylist of deadlines
                            deadlines.add(String.valueOf((int)((task.getDeadline().getTime() - new Date().getTime()) / 3600000)));

                            // Calculates and adds the estimated completion time based on whether it's early or late (overdue)
                            if (new Date().before(task.getDeadline())) {
                                estimatedCompletionTimes.add(String.valueOf(Integer.parseInt(averageTimes[0]) + (int)((new Date().getTime() - task.getDeadline().getTime()) / 3600000)));
                            }
                            else {
                                estimatedCompletionTimes.add(String.valueOf(Integer.parseInt(averageTimes[1]) + (int)((new Date().getTime() - task.getDeadline().getTime()) / 3600000)));
                            }
                        }
                    }

                    // Generates a chart displaying the estimated completion times
                    if (deadlines.size() > 0) {
                        grapher.graphCompletionTimes(deadlines.toArray(new String[0]), estimatedCompletionTimes.toArray(new String[0]));
                    }
                    else {
                        // If there are no incomplete tasks, the user is told there is not enough data to generate the chart
                        JOptionPane.showMessageDialog(null,"There is not enough data to generate this chart.", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which initializes the data displayed in the progress and analytics panels of the progressGUI
     * @author Hadi
     * @throws IOException An exception caused by file I/O
     */
    public void initializePanels() throws IOException {
        // Iterates through every task in the system and retrieves task progress data
        int incompleteTasks = 0, inProgressTasks = 0, completeTasks = 0, overDueTasks = 0, totalTasks = 0, aheadOfSchedule = 0;
        for (Task task : getTasks()) {
            if (task.getStatus().equals("Incomplete")) incompleteTasks++;
            else if (task.getStatus().equals("In Progress")) inProgressTasks++;
            else if (!(new Date().before(task.getDeadline()))) completeTasks++;
            else {
                aheadOfSchedule++;
                completeTasks++;
            }

            if (new Date().after(task.getDeadline()) && !task.getStatus().equals("Complete")) overDueTasks++;
            totalTasks++;
        }

        // Configures the Progress panel with progress data
        GUI.setProgressBar((int)(100 * (double)((double)completeTasks / (double)totalTasks)));
        GUI.setTime(String.valueOf((int)(100 * (double)((double)aheadOfSchedule / (double)totalTasks)) + "% Ahead of Schedule"));
        GUI.setPendingTasks(String.valueOf(inProgressTasks + incompleteTasks) + " Task(s) to be Completed");
        GUI.setWorkload(String.valueOf(overDueTasks) + " task(s) overdue");
        GUI.setProgress(String.valueOf((int)(100 * (double)((double)completeTasks / (double)totalTasks)) + "% Complete"));
        GUI.setIncompleteTasks(String.valueOf(incompleteTasks) + " task(s)");
        GUI.setInProgressTasks(String.valueOf(inProgressTasks) + " task(s)");
        GUI.setCompletedTasks(String.valueOf(completeTasks) + " task(s)");

        // Retrieves analytical data
        String mostProductiveDay = "";
        int mostProductiveDayTasks = -1;
        String leastProductiveDay = "";
        int leastProductiveDayTasks = Integer.MAX_VALUE;

        // Reads the daily tasks file and determines the least and most productive work days based on the number of completed tasks
        String line = "";
        BufferedReader dailyTasksReader = new BufferedReader(new FileReader("assets\\daily_tasks.csv"));
        while ((line = dailyTasksReader.readLine()) != null) {
            String[] data = line.split("~");
            if (Integer.parseInt(data[1]) > mostProductiveDayTasks) {
                mostProductiveDay = data[0];
                mostProductiveDayTasks = Integer.parseInt(data[1]);
            }
            if (Integer.parseInt(data[1]) < leastProductiveDayTasks) {
                leastProductiveDay = data[0];
                leastProductiveDayTasks = Integer.parseInt(data[1]);
            }
        }

        // Configures the Analytics panel with the program's analytical data
        GUI.setMostProductiveDay(mostProductiveDay);
        GUI.setLeastProductiveDay(leastProductiveDay);
        String[] averageTimes = getAverageTimes();
        GUI.setEarlyTaskCompletion(averageTimes[0] + " hours");
        GUI.setLateTaskCompletion("+" + averageTimes[1] + " hours");
    }

    /**
     * Method which returns the task progress button's ActionListener
     * @author Hadi
     * @return The task progress button's ActionListener
     */
    public ActionListener getTaskProgressButtonAction() {
        return taskProgressButtonAction;
    }

    /**
     * Method which returns the completion statistics button's ActionListener
     * @author Hadi
     * @return The completion statistics button's ActionListener
     */
    public ActionListener getCompletionStatsButtonAction() {
        return completionStatsButtonAction;
    }

    /**
     * Method which returns the completion times button's ActionListener
     * @author Hadi
     * @return The completion times button's ActionListner
     */
    public ActionListener getCompletionTimesButtonAction() {
        return completionTimesButtonAction;
    }
}
