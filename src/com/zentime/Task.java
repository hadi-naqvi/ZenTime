/**
 * ZenTime | Task.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import java.util.Date;

public class Task extends Reminder {
    // Declaration of task attributes
    private String type, status;
    private Date dateAdded, deadline, dateCompleted;
    private boolean notifications;

    /**
     * Constructor for Task objects which initializes its attributes
     * @author Hadi
     * @param name The name of the task
     * @param description The task's description
     * @param type The type of task
     * @param dateAdded The date and time the task was created
     * @param deadline The date and time of the task's deadline
     * @param dateCompleted The date and time of the task's completion
     * @param notificationTime The date/time the task will send a notification
     * @param notifications If the user wishes to receive notifications for their task
     * @param status The status of the task's completion
     */
    public Task(String name, String description, String type, Date dateAdded, Date deadline, Date dateCompleted, Date notificationTime, boolean notifications, String status) {
        // Initialization of Task attributes
        super(name, description, notificationTime);
        this.type = type;
        this.dateAdded = dateAdded;
        this.deadline = deadline;
        this.dateCompleted = dateCompleted;
        this.notifications = notifications;
        this.status = status;
    }

    /**
     * Setter method for the task's date of completion
     * @author Hadi
     * @param date The date completed
     */
    public void setDateCompleted(Date date) {
        this.dateCompleted = date;
    }

    /**
     * Getter method for the task's type
     * @author Hadi
     * @return The type of task
     */
    public String getType() {
        return type;
    }

    /**
     * Getter method for date/time task was added
     * @author Hadi
     * @return The date and time of when task was added
     */
    public Date getDateAdded() {
        return dateAdded;
    }

    /**
     * Getter method for the task's deadline
     * @author Hadi
     * @return The date and time of the task's deadline
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Getter method for the task's Completion Date
     * @author Hadi
     * @return The task's date of completion
     */
    public Date getDateCompleted() {
        return dateCompleted;
    }

    /**
     * Getter method for notification setting
     * @author Hadi
     * @return If the task has notifications enabled
     */
    public boolean hasNotifications() {
        return notifications;
    }

    /**
     * This method returns the status of the task
     * @author Hadi
     * @return The status of the task
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method sets a new status for the task
     * @author Hadi
     * @param newStatus
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }
}