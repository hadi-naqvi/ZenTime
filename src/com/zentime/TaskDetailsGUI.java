/**
 * ZenTime | TaskDetailsGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

public class TaskDetailsGUI extends SmallGUI {
    private TaskDetailsController controller;
    private JLabel taskNameLabel, taskName, taskDescriptionLabel, timeAddedLabel,
            timeAdded, deadlineLabel, deadline, timeCompletedLabel, timeCompleted,
            taskStatusLabel, taskStatus, taskTypeLabel, taskType, notificationsLabel, notifications;
    private JTextArea taskDescription;
    private JScrollPane scrollableTaskDescription;
    private JGradientButton startFinishButton;

    /**
     * Constructor method for the TaskDetailsGUI
     * @author Hadi
     * @param scheduler The object/instance of the SchedulerGUI
     * @param taskRow The row/index number of the selected task
     * @throws IOException An exception caused by file I/O
     */
    public TaskDetailsGUI(SchedulerGUI scheduler, int taskRow) throws IOException {
        // Initialization of class attributes and GUI's JComponents
        super("View Details");
        this.controller = new TaskDetailsController(this, scheduler, taskRow);
        this.taskNameLabel = new JLabel("Task Name: ");
        this.taskName = new JLabel();
        this.taskDescriptionLabel = new JLabel("Task Description: ");
        this.timeAddedLabel = new JLabel("Time Added: ");
        this.timeAdded = new JLabel();
        this.deadlineLabel = new JLabel("Deadline:");
        this.deadline = new JLabel();
        this.timeCompletedLabel = new JLabel("Time Completed: ");
        this.timeCompleted = new JLabel();
        this.taskStatusLabel = new JLabel("Status: ");
        this.taskStatus = new JLabel();
        this.taskTypeLabel = new JLabel("Task Type: ");
        this.taskType = new JLabel();
        this.notificationsLabel = new JLabel("Notifications: ");
        this.notifications = new JLabel();
        this.taskDescription = new JTextArea();
        this.scrollableTaskDescription = new JScrollPane(taskDescription);
        this.startFinishButton = new JGradientButton("Start/Finish");
        panel.setLayout(null);

        // Sets label for task name
        taskNameLabel.setFont(headingFont);
        taskNameLabel.setBounds(20, -40, 200, 200);
        panel.add(taskNameLabel);

        // Shows user the name of the task
        taskName.setFont(lightFont);
        taskName.setBounds(140, -40, 200, 200);
        panel.add(taskName);

        // Sets label for task description
        taskDescriptionLabel.setFont(headingFont);
        taskDescriptionLabel.setBounds(20, 0, 200, 200);
        panel.add(taskDescriptionLabel);

        // Shows textbox and the description of the task
        taskDescription.setLineWrap(true);
        taskDescription.setEditable(false);
        scrollableTaskDescription.setBounds(20, 130, 330, 200);
        scrollableTaskDescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollableTaskDescription);

        // Sets label for time added
        timeAddedLabel.setFont(headingFont);
        timeAddedLabel.setBounds(20, 260, 200, 200);
        panel.add(timeAddedLabel);

        // Shows actual time when the task was added
        timeAdded.setFont(lightFont);
        timeAdded.setBounds(140, 260, 200, 200);
        panel.add(timeAdded);

        deadlineLabel.setFont(headingFont);
        deadlineLabel.setBounds(20, 300, 200, 200);
        panel.add(deadlineLabel);

        deadline.setFont(lightFont);
        deadline.setBounds(115, 300, 200, 200);
        panel.add(deadline);

        // Sets label for time completed
        timeCompletedLabel.setFont(headingFont);
        timeCompletedLabel.setBounds(20, 340, 200, 200);
        panel.add(timeCompletedLabel);

        // Shows actual time when the task was completed
        timeCompleted.setFont(lightFont);
        timeCompleted.setBounds(180, 340, 200, 200);
        panel.add(timeCompleted);

        // Sets label for status of the task
        taskStatusLabel.setFont(headingFont);
        taskStatusLabel.setBounds(20, 380, 200, 200);
        panel.add(taskStatusLabel);

        // Shows actual status of the task
        taskStatus.setFont(lightFont);
        taskStatus.setBounds(95, 380, 200, 200);
        panel.add(taskStatus);

        // Sets label for task type
        taskTypeLabel.setFont(headingFont);
        taskTypeLabel.setBounds(20, 420, 200, 200);
        panel.add(taskTypeLabel);

        // Shows actual task type
        taskType.setFont(lightFont);
        taskType.setBounds(130, 420, 200, 200);
        panel.add(taskType);

        // Sets label for notifications
        notificationsLabel.setFont(headingFont);
        notificationsLabel.setBounds(20, 460, 200, 200);
        panel.add(notificationsLabel);

        // Shows actual notifications option
        notifications.setFont(lightFont);
        notifications.setBounds(150, 460, 200, 200);
        panel.add(notifications);

        startFinishButton.setBounds(35, 585, 125, 50);
        panel.add(startFinishButton);

        // Adding ActionListener objects to buttons
        closeButton.addActionListener(controller.getCloseButtonAction());
        startFinishButton.addActionListener(controller.getStartFinishButtonAction());
        controller.setTaskDetails();

        setVisible(true);
    }

    /**
     * Method which sets the name's text to a new name
     * @author Hadi
     * @param name The new name
     */
    public void setTaskName(String name) {
        taskName.setText(name);
    }

    /**
     * Method which sets the description's text to a new description
     * @author Hadi
     * @param description The new description
     */
    public void setTaskDescription(String description) {
        taskDescription.setText(description);
    }

    /**
     * Method which sets the time added's text to a new time added
     * @author Hadi
     * @param time The new time added
     */
    public void setTimeAdded(Date time) {
        timeAdded.setText(String.valueOf(time));
    }

    /**
     * Method which sets the deadline's text to a new deadline
     * @author Hadi
     * @param date The new deadline
     */
    public void setDeadline(Date date) {
        deadline.setText(String.valueOf(date));
    }

    /**
     * Method which sets the time completed to a new time completed
     * @author Hadi
     * @param time The new time completed
     */
    public void setTimeCompleted(String time) {
        timeCompleted.setText(time);
    }

    /**
     * Method which sets a new status
     * @author Hadi
     * @param status The new status
     */
    public void setStatus(String status) {
        taskStatus.setText(status);
    }

    /**
     * Method which sets a new task type
     * @author Hadi
     * @param type The new task type
     */
    public void setTaskType(String type) {
        taskType.setText(type);
    }

    /**
     * Method which changes the notification setting's text to a new notification setting
     * @author Hadi
     * @param notificationSetting The new notification setting (True/False)
     */
    public void setNotifications(boolean notificationSetting) {
        notifications.setText((notificationSetting) ? "Yes" : "No");
    }
}