/**
 * ZenTime | RemindersController.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class RemindersController extends EventHandler {
    // Declaration of class attributes
    private RemindersGUI GUI;
    private ActionListener addButtonAction, removeButtonAction, updateButtonAction, chooseReminderAction;

    /**
     * Constructor method for RemindersGUI's controller class
     * @author Hadi
     * @param remindersGUI An instance of the Reminders GUI
     */
    public RemindersController(RemindersGUI remindersGUI) throws IOException {
        // Initialization of attributes
        super();
        this.GUI = remindersGUI;
        this.addButtonAction = modify("Add");
        this.updateButtonAction = modify("Edit");
        this.removeButtonAction = remove();
        this.chooseReminderAction = chooseReminder();
    }

    /**
     * Method which validates a reminder based on its details and provides an error message if it's invalid
     * @author Hadi
     * @param name The name of the reminder
     * @param description The description/message of the reminder's notification
     * @param time The time the reminder will send a notification
     * @param type The type of action (Adding or editing)
     * @return Whether the reminder is valid, or it's error message if it's invalid
     * @throws IOException An exception caused by file I/O
     */
    private String validateReminder(String name, String description, Date time, String type) throws IOException {
        // Initialization of validity of reminder and error message
        boolean isValid = true;
        String errorMessage = "Error: Reminder could not be added/updated for the following reason:";

        // Checks if the name and description are empty (They must be filled out)
        if (name.equals("") || description.equals("") || (GUI.getEditReminderIndex() == -1) && type.equals("Edit")) {
            isValid = false;
            errorMessage += "\n- You have not filled in all the required fields";
        }

        // Checks if the name or description contain "~" (Cannot contain this character)
        if (name.contains("~") || description.contains("~")) {
            isValid = false;
            errorMessage += "\n- You may not enter the symbol '~' in your reminder name/description";
        }

        // Checks if the notification time is in the past (Cannot create a reminder in the past)
        if (time.before(new Date())) {
            isValid = false;
            errorMessage += "\n- You cannot add a reminder with a time in a past";
        }

        // Returns the validation of the reminder, and its error message if it's invalid
        return (isValid) ? "Valid" : errorMessage;
    }

    /**
     * Method which creates the ActionListener object for the add/update buttons
     * @author Hadi
     * @param type The type of action (Add/Edit)
     * @return The ActionListener object
     */
    private ActionListener modify(String type) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Creates a confirmation popup dialog asking the user if they would like to add/edit the reminder
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + type + " this reminder?", "Confirm Reminder",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    // If the user confirms adding/editing the reminder, the reminder is added/edited
                    if (confirmation == 0) {
                        // Validates the reminder
                        String validation = validateReminder(GUI.getReminderName(), GUI.getReminderMessage(), GUI.getReminderTime(), type);;
                        if (type.equals("Edit")) {
                            validation = validateReminder(GUI.getEditReminderName(), GUI.getEditReminderMessage(), GUI.getEditReminderTime(), type);
                        }

                        // If the reminder is valid, it's added/updated in the system's list of reminders
                        if (validation.equals("Valid")) {
                            // Retrieves the reminder
                            importItems("reminder");
                            getReminders();

                            // Adds the reminder to the system's list of reminders
                            if (type.equals("Add")) {
                                addNewReminder(new Reminder(GUI.getReminderName(), GUI.getReminderMessage(), GUI.getReminderTime()));
                            }
                            // Updates the reminder in the system's list of reminders
                            else {
                                updateReminder(GUI.getEditReminderIndex(), new Reminder(GUI.getEditReminderName(), GUI.getEditReminderMessage(), GUI.getEditReminderTime()));
                            }

                            // Updates the list of reminders stored locally in the program's .csv file
                            updateItems("reminder");

                            // Closes the window and creates a popup dialog showing the user the result of their action
                            GUI.dispose();
                            JOptionPane.showMessageDialog(null, "Successfully " + type + "ed reminder!");
                        }
                        // If the reminder is invalid, the user is shown an error message explaining why it's invalid
                        else {
                            JOptionPane.showMessageDialog(null, validation, "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates the ActionListener object for the delete button
     * @author Hadi
     * @return The ActionListener object
     */
    private ActionListener remove() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Creates a confirmation popup dialog asking the user if they would like to delete the reminder they've selected
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this reminder?", "Confirm Reminder",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    // If the user wishes to delete the reminder, the reminder is deleted
                    if (confirmation == 0) {
                        // Retrieves the reminder and removes it from the system's list of reminders and updates the .csv file of reminders
                        int index = GUI.getEditReminderIndex();
                        removeReminder(getReminder(index));
                        updateItems("reminder");

                        // Closes the window and shows the user the result of their action in a popup dialog
                        GUI.dispose();
                        JOptionPane.showMessageDialog(null, "Successfully deleted reminder!");
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates the ActionListener object for the JComboBoxes
     * @author Hadi
     * @return The ActionListener object
     */
    private ActionListener chooseReminder() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Sets a new list of date numbers based on the selected month/year
                    Reminder reminder = getReminder(GUI.getEditReminderIndex());
                    GUI.setEditReminderDetails(reminder.getName(), reminder.getDescription(),
                            String.valueOf(reminder.getNotificationTime()).substring(11, 13),
                            String.valueOf(reminder.getNotificationTime()).substring(14, 16));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which sets the reminder details of the initial/first reminder in the GUI
     * @author Hadi
     * @throws IOException An exception caused by file I/O
     */
    public void initializeEditMenu() throws IOException {
        // If a reminer exists in the system, the first reminder's details are filled into the GUI components
        if (getReminders().size() > 0) {
            Reminder reminder = getReminder(0);
            GUI.setEditReminderDetails(reminder.getName(), reminder.getDescription(),
                    String.valueOf(reminder.getNotificationTime()).substring(11, 13),
                    String.valueOf(reminder.getNotificationTime()).substring(14, 16));
        }
    }

    /**
     * Method which populates the JComboBox with the system's list of reminders
     * @author Hadi
     * @throws IOException An exception caused by file I/O
     */
    public void populateReminders() throws IOException {
        for (Reminder reminder : getReminders()) {
            GUI.addReminder(reminder.getName());
        }
    }

    /**
     * Method which returns the add button's ActionListener object
     * @author Hadi
     * @return The ActionListener of the add button
     */
    public ActionListener getAddButtonAction() {
        return addButtonAction;
    }

    /**
     * Method which returns the remove button's ActionListener object
     * @author Hadi
     * @return The ActionListener of the remove button
     */
    public ActionListener getRemoveButtonAction() {
        return removeButtonAction;
    }

    /**
     * Method which returns the update button's ActionListener object
     * @author Hadi
     * @return The ActionListener of the update button
     */
    public ActionListener getUpdateButtonAction() {
        return updateButtonAction;
    }

    /**
     * Method which returns the ActionListener object for the JComboBox
     * @author Hadi
     * @return The JComboBox's ActionListener
     */
    public ActionListener getChooseReminderAction() {
        return chooseReminderAction;
    }
}
