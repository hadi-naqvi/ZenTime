/**
 * ZenTime | Reminder.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import java.util.Date;

public class Reminder {
    // Declaration of class attributes
    private String name, description;
    private Date notificationTime;

    /**
     * Constructor method which initializes task attributes
     * @author Hadi
     * @param name The name of the item
     * @param description The item's description/message
     * @param notificationTime The date/time the item will send a notification
     */
    protected Reminder(String name, String description, Date notificationTime) {
        // Initializes attributes
        this.name = name;
        this.description = description;
        this.notificationTime = notificationTime;
    }

    /**
     * Getter method for the Item's name
     * @author Hadi
     * @return The name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for description
     * @author Hadi
     * @return The description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter method for the notification time
     * @author Hadi
     * @return The date/time the item will send a notification
     */
    public Date getNotificationTime() {
        return notificationTime;
    }
}