/**
 * ZenTime | RemindersGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

public class RemindersGUI extends LargeGUI {
    // Declaration of class attributes
    private RemindersController controller;
    private JLabel selectTimeLabel, timeSymbol, reminderNameLabel,
            reminderMessageLabel, selectReminderLabel, editTimeLabel,
            secondTimeSymbol, editReminderNameLabel, editReminderMessageLabel;
    private JComboBox chooseHour, chooseMinute, chooseReminder, editHour, editMinute;
    private JTextField reminderName, editReminderName;
    private JTextArea reminderMessage, editReminderMessage;
    private JGradientButton addButton, removeButton, updateButton;
    private JScrollPane scrollableReminderMessage, scrollableEditReminderMessage;

    /**
     * Constructor method for Reminders GUI
     * @author Hadi
     */
    public RemindersGUI() throws IOException {
        // Initialization of attributes
        super("Reminders", "Add Reminder", "Edit Reminder");
        this.controller = new RemindersController(this);

        // Initialization of JComponents in the GUI
        this.selectTimeLabel = new JLabel("Select A Time:");
        this.chooseHour = new JComboBox();
        this.timeSymbol = new JLabel(":");
        this.chooseMinute = new JComboBox();
        this.reminderNameLabel = new JLabel("Enter Reminder Name:");
        this.reminderName = new JTextField();
        this.reminderMessageLabel = new JLabel("Enter Reminder Message:");
        this.reminderMessage = new JTextArea();
        this.scrollableReminderMessage = new JScrollPane(reminderMessage);
        this.addButton = new JGradientButton("Add");
        this.selectReminderLabel = new JLabel("Select A Reminder:");
        this.chooseReminder = new JComboBox();
        this.editTimeLabel = new JLabel("Edit Time:");
        this.editHour = new JComboBox();
        this.secondTimeSymbol = new JLabel(":");
        this.editMinute = new JComboBox();
        this.editReminderNameLabel = new JLabel("Edit Reminder Name:");
        this.editReminderName = new JTextField();
        this.editReminderMessageLabel = new JLabel("Edit Reminder Message:");
        this.editReminderMessage = new JTextArea();
        this.scrollableEditReminderMessage = new JScrollPane(editReminderMessage);
        this.removeButton = new JGradientButton("Remove");
        this.updateButton = new JGradientButton("Update");

        // Sets up left panel
        leftPanel.setLayout(null);
        leftPanel.setBounds(140, 130, 450, 500);
        leftTitle.setBounds(0, -200, 450, 450);
        leftPanel.add(leftTitle);

        // Creates select time label
        selectTimeLabel.setFont(smallFont);
        selectTimeLabel.setBounds(30, -35, 200, 200);
        leftPanel.add(selectTimeLabel);

        // Allows user to choose an hour
        chooseHour.setBounds(30, 85, 100, 25);
        leftPanel.add(chooseHour);

        // Symbol that separates hours and minutes
        timeSymbol.setBounds(140, 85, 100, 25);
        leftPanel.add(timeSymbol);

        // Allows user to choose the minute
        chooseMinute.setBounds(155, 85, 100, 25);
        leftPanel.add(chooseMinute);

        // Creates reminder name label
        reminderNameLabel.setFont(smallFont);
        reminderNameLabel.setBounds(30, 40, 300, 200);
        leftPanel.add(reminderNameLabel);

        // Allows user to enter reminder name
        reminderName.setBounds(30, 160, 385, 25);
        leftPanel.add(reminderName);

        // Creates reminder message label
        reminderMessageLabel.setFont(smallFont);
        reminderMessageLabel.setBounds(30, 165, 300, 100);
        leftPanel.add(reminderMessageLabel);

        // Allows user to enter reminder message
        reminderMessage.setLineWrap(true);
        scrollableReminderMessage.setBounds(30, 235, 388, 200);
        scrollableReminderMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftPanel.add(scrollableReminderMessage);

        // Add button
        addButton.setFont(smallFont);
        addButton.setBounds(160, 443, 125, 50);
        leftPanel.add(addButton);

        // Sets up right panel
        rightPanel.setLayout(null);
        rightPanel.setBounds(640, 130, 450, 500);
        rightTitle.setBounds(0, -200, 450, 450);
        rightPanel.add(rightTitle);

        // Creates select reminder label
        selectReminderLabel.setFont(smallFont);
        selectReminderLabel.setBounds(30, 15, 200, 100);
        rightPanel.add(selectReminderLabel);

        // Allows user to choose a reminder
        chooseReminder.setBounds(30, 85, 300, 30);
        rightPanel.add(chooseReminder);

        // Creates edit time label
        editTimeLabel.setFont(smallFont);
        editTimeLabel.setBounds(30, 85, 200, 100);
        rightPanel.add(editTimeLabel);

        // Allows user to edit the hour
        editHour.setBounds(30, 155, 100, 25);
        rightPanel.add(editHour);

        // Symbol that separates hours and minutes
        secondTimeSymbol.setBounds(140, 155, 100, 25);
        rightPanel.add(secondTimeSymbol);

        // Allows user to edit the minute
        editMinute.setBounds(155, 155, 100, 25);
        rightPanel.add(editMinute);

        // Creates edit reminder name label
        editReminderNameLabel.setFont(smallFont);
        editReminderNameLabel.setBounds(30, 155, 200, 100);
        rightPanel.add(editReminderNameLabel);

        // Allows user to enter reminder name
        editReminderName.setBounds(30, 225, 385, 25);
        rightPanel.add(editReminderName);

        // Creates edit reminder message label
        editReminderMessageLabel.setFont(smallFont);
        editReminderMessageLabel.setBounds(30, 225, 300, 100);
        rightPanel.add(editReminderMessageLabel);

        // Allows user to edit reminder message
        editReminderMessage.setLineWrap(true);
        scrollableEditReminderMessage.setBounds(30, 295, 388, 140);
        scrollableEditReminderMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rightPanel.add(scrollableEditReminderMessage);

        // Creates remove button
        removeButton.setFont(smallFont);
        removeButton.setBounds(80, 443, 125, 50);
        rightPanel.add(removeButton);

        // Creates update button
        updateButton.setFont(smallFont);
        updateButton.setBounds(250, 443, 125, 50);
        rightPanel.add(updateButton);

        // Adds the different selectable timeslots to the ComboBoxes
        for (int i = 0; i <= 23; i++) {
            chooseHour.addItem(Integer.toString(i));
            editHour.addItem(Integer.toString(i));
        }
        for (int i = 0; i <= 59; i++) {
            chooseMinute.addItem(Integer.toString(i));
            editMinute.addItem(Integer.toString(i));
        }

        // Adding ActionListeners to buttons
        controller.populateReminders();
        controller.initializeEditMenu();
        addButton.addActionListener(controller.getAddButtonAction());
        removeButton.addActionListener(controller.getRemoveButtonAction());
        updateButton.addActionListener(controller.getUpdateButtonAction());
        chooseReminder.addActionListener(controller.getChooseReminderAction());

        setVisible(true);
    }

    /**
     * Method which sets the reminder details of the reminder being edited
     * @author Hadi
     * @param name The reminder's name
     * @param description The reminder's description
     * @param hour The reminder's notification hour
     * @param minute The reminder's notification minute
     */
    public void setEditReminderDetails(String name, String description, String hour, String minute) {
        editReminderName.setText(name);
        editReminderMessage.setText(description);
        editHour.setSelectedItem(hour);
        editMinute.setSelectedItem(minute);
    }

    /**
     * Method which adds a reminder to the JComboBox of reminders
     * @author Hadi
     * @param name The name of the reminder
     */
    public void addReminder(String name) {
        chooseReminder.addItem(name);
    }

    /**
     * Method which retrieves the notification time of the reminder being added
     * @author Hadi
     * @return The notification time
     */
    public Date getReminderTime() {
        // Creates and returns a date object based on the selected values in the hour/minute JComboBoxes
        String time = chooseHour.getSelectedItem() + ":" + String.valueOf(chooseMinute.getSelectedItem()) + ":00";
        String currentDate = String.valueOf(new Date());
        return new Date(currentDate.substring(0, 10) + " " + time + currentDate.substring(19, 28));
    }

    /**
     * Method which retrieves the name of the reminder the user entered
     * @author Hadi
     * @return The name of the reminder
     */
    public String getReminderName() {
        return reminderName.getText();
    }

    /**
     * Method which retrieves the message of the reminder the user entered
     * @author Hadi
     * @return The reminder message
     */
    public String getReminderMessage() {
        return reminderMessage.getText();
    }

    /**
     * Method which retrieves the index number of the reminder the user selected to edit
     * @author Hadi
     * @return The index of the reminder
     */
    public int getEditReminderIndex() {
        return chooseReminder.getSelectedIndex();
    }

    /**
     * Method which retrieves the name of the reminder the user chose to edit
     * @author Hadi
     * @return The name of the reminder
     */
    public String getEditReminderName() {
        return editReminderName.getText();
    }

    /**
     * Method which retrieves the message of the reminder the user chose to edit
     * @author Hadi
     * @return The message of the reminder
     */
    public String getEditReminderMessage() {
        return editReminderMessage.getText();
    }

    /**
     * Method which retrieves the notification time of the task the user chose to edit
     * @author Hadi
     * @return The notification time of the reminder
     */
    public Date getEditReminderTime() {
        String time = editHour.getSelectedItem() + ":" + String.valueOf(editMinute.getSelectedItem());
        String currentDate = String.valueOf(new Date());
        return new Date(currentDate.substring(0, 10) + " " + time + currentDate.substring(19, 28));
    }
}