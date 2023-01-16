/**
 * ZenTime | ProgressGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ProgressGUI extends LargeGUI {
    // Declaration of class attributes and GUI components
    private ProgressController controller;
    private JPanel midPanel;
    private JProgressBar taskCompletionProgress;
    private JLabel midTitle, timeLabel, tasksLabel, workloadLabel, progressLabel, incompleteTasksLabel,
            inProgressTasksLabel, completedTasksLabel, mostProductiveDay, leastProductiveDay,
            earlyTaskCompletion, lateTaskCompletion;
    private JGradientButton taskProgressButton, completionStatsButton, completionTimesButton, closeButton;

    /**
     * Constructor method for the Progress Reports GUI
     * @author Hadi
     * @throws IOException An exception caused by file I/O
     */
    public ProgressGUI() throws IOException {
        // Initialization of class attributes and GUI components
        super("Progress Reports", "Progress", "Charts");
        this.controller = new ProgressController(this);
        this.midPanel = new JPanel();
        this.taskCompletionProgress = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        this.midTitle = new JLabel("Analytics", SwingConstants.CENTER);
        this.timeLabel = new JLabel("Time:   ");
        this.tasksLabel = new JLabel("Pending Tasks:   ");
        this.workloadLabel = new JLabel("Workload:   ");
        this.progressLabel = new JLabel("Progress:   ");
        this.incompleteTasksLabel = new JLabel("Incomplete Tasks:   ");
        this.inProgressTasksLabel = new JLabel("In Progress Tasks:   ");
        this.completedTasksLabel = new JLabel("Completed Tasks:   ");
        this.mostProductiveDay = new JLabel("Most Productive Day:   ");
        this.leastProductiveDay = new JLabel("Least Productive Day:   ");
        this.earlyTaskCompletion = new JLabel("Average Early Completion Time:   ");
        this.lateTaskCompletion = new JLabel("Average Late Completion Time:   ");
        this.taskProgressButton = new JGradientButton("Task Progress");
        this.completionStatsButton = new JGradientButton("Completion Statistics");
        this.completionTimesButton = new JGradientButton("Completion Times");
        this.closeButton = new JGradientButton("Close");

        // Configuration of the left panel
        leftPanel.setLayout(new GridLayout(9, 1, 0, 30));
        leftPanel.setBounds(20, 125, 425, 515);
        leftTitle.setBounds(60, 6, 300, 34);
        leftPanel.add(leftTitle);

        // Configuration of left panel JProgressBar
        taskCompletionProgress.setStringPainted(true);
        leftPanel.add(taskCompletionProgress);

        // Configuration of left panel JLabels
        timeLabel.setFont(headingFont);
        tasksLabel.setFont(headingFont);
        workloadLabel.setFont(headingFont);
        progressLabel.setFont(headingFont);
        incompleteTasksLabel.setFont(headingFont);
        inProgressTasksLabel.setFont(headingFont);
        completedTasksLabel.setFont(headingFont);
        leftPanel.add(timeLabel);
        leftPanel.add(tasksLabel);
        leftPanel.add(workloadLabel);
        leftPanel.add(progressLabel);
        leftPanel.add(incompleteTasksLabel);
        leftPanel.add(inProgressTasksLabel);
        leftPanel.add(completedTasksLabel);

        // Configuration of the middle panel
        midPanel.setLayout(new GridLayout(5, 1));
        midPanel.setBounds(470, 125, 425, 515);
        midPanel.setBackground(new Color(80, 150, 255));
        midPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        midTitle.setBounds(435, 6, 300, 34);
        midTitle.setFont(largeFont);
        midPanel.add(midTitle);
        add(midPanel);

        // Configuration of middle panel JLabels
        mostProductiveDay.setFont(headingFont);
        leastProductiveDay.setFont(headingFont);
        earlyTaskCompletion.setFont(headingFont);
        lateTaskCompletion.setFont(headingFont);
        midPanel.add(mostProductiveDay);
        midPanel.add(leastProductiveDay);
        midPanel.add(earlyTaskCompletion);
        midPanel.add(lateTaskCompletion);

        // Configuration of the right panel
        rightPanel.setLayout(new GridLayout(5, 1, 0, 30));
        rightPanel.setBounds(920, 125, 325, 515);
        rightTitle.setBounds(945, 125, 300, 34);
        rightPanel.add(rightTitle);

        // Configuration of right panel buttons
        taskProgressButton.setFont(smallFont);
        completionStatsButton.setFont(smallFont);
        completionTimesButton.setFont(smallFont);
        closeButton.setFont(smallFont);
        rightPanel.add(taskProgressButton);
        rightPanel.add(completionStatsButton);
        rightPanel.add(completionTimesButton);
        rightPanel.add(closeButton);

        // Adding ActionListeners to the buttons in the GUI
        taskProgressButton.addActionListener(controller.getTaskProgressButtonAction());
        completionStatsButton.addActionListener(controller.getCompletionStatsButtonAction());
        completionTimesButton.addActionListener(controller.getCompletionTimesButtonAction());
        closeButton.addActionListener(controller.getCloseButtonAction());

        // Initializes the progress and analytics panels with their data
        controller.initializePanels();

        setVisible(true);
    }

    /**
     * Method which sets the progress bar's percentage
     * @author Hadi
     * @param percentage
     */
    public void setProgressBar(int percentage) {
        taskCompletionProgress.setValue(percentage);
    }

    /**
     * Method which sets the progress time (% Ahead of schedule) in the Progress panel
     * @author Hadi
     * @param time The progress time
     */
    public void setTime(String time) {
        timeLabel.setText(timeLabel.getText() + time);
    }

    /**
     * Method which sets the pending tasks (# of tasks to be completed) in the Progress panel
     * @author Hadi
     * @param pendingTasks The number of pending tasks
     */
    public void setPendingTasks(String pendingTasks) {
        tasksLabel.setText(tasksLabel.getText() + pendingTasks);
    }

    /**
     * Method which sets the workload (# of overdue tasks) in the Progress panel
     * @author Hadi
     * @param workload The number of overdue tasks
     */
    public void setWorkload(String workload) {
        workloadLabel.setText(workloadLabel.getText() + workload);
    }

    /**
     * Method which sets the progress (# of completed tasks) in the Progress panel
     * @author Hadi
     * @param progress The percentage of completed tasks
     */
    public void setProgress(String progress) {
        progressLabel.setText(progressLabel.getText() + progress);
    }

    /**
     * Method which sets the number of incomplete tasks in the Progress panel
     * @author Hadi
     * @param incompleteTasks The number of incomplete tasks
     */
    public void setIncompleteTasks(String incompleteTasks) {
        incompleteTasksLabel.setText(incompleteTasksLabel.getText() + incompleteTasks);
    }

    /**
     * Method which sets the number of in progress tasks in the Progress panel
     * @author Hadi
     * @param inProgressTasks The number of in progress tasks
     */
    public void setInProgressTasks(String inProgressTasks) {
        inProgressTasksLabel.setText(inProgressTasksLabel.getText() + inProgressTasks);
    }

    /**
     * Method which sets the number of completed tasks in the Progress panel
     * @author Hadi
     * @param completedTasks The number of completed tasks
     */
    public void setCompletedTasks(String completedTasks) {
        completedTasksLabel.setText(completedTasksLabel.getText() + completedTasks);
    }

    /**
     * Method which sets the most productive work day in the analytics panel
     * @author Hadi
     * @param day The day
     */
    public void setMostProductiveDay(String day) {
        mostProductiveDay.setText(mostProductiveDay.getText() + day);
    }

    /**
     * Method which sets the least productive work day in the analytics panel
     * @author Hadi
     * @param day The day
     */
    public void setLeastProductiveDay(String day) {
        leastProductiveDay.setText(leastProductiveDay.getText() + day);
    }

    /**
     * Method which sets the average early completion time for tasks in the analytics panel
     * @author Hadi
     * @param hours The number of hours early
     */
    public void setEarlyTaskCompletion(String hours) {
        earlyTaskCompletion.setText(earlyTaskCompletion.getText() + hours);
    }

    /**
     * Method which sets the average late completion time for tasks in the analytics panel
     * @author Hadi
     * @param hours The number of hours late
     */
    public void setLateTaskCompletion(String hours) {
        lateTaskCompletion.setText(lateTaskCompletion.getText() + hours);
    }
}
