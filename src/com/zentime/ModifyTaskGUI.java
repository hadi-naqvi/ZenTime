/**
 * ZenTime | ModifyTaskGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModifyTaskGUI extends SmallGUI {
    private String type;
    private ModifyTaskController controller;
    private SchedulerGUI schedulerGUI;
    private JLabel enterTaskNameLabel, enterTaskDescriptionLabel, enterDateTimeLabel, timeSymbol, typeLabel, notificationsLabel;
    private JTextField enterTaskName;
    private JTextArea enterTaskDescription;
    private JScrollPane scrollableEnterTaskDescription;
    private JComboBox chooseMonth, chooseDate, chooseHour, chooseMinute, chooseYear;
    private ButtonGroup taskTypes;
    private JRadioButton timedTask, taskWithDeadline, event;
    private JCheckBox notifications;
    private JGradientButton addButton, deleteButton, updateButton;

    /**
     * Constructor method for ModifyTaskGUI
     * @author Hadi
     * @param GUIType The type of ModifyTaskGUI (Add or Edit)
     * @throws Exception An exception caused by the super constructor
     */
    public ModifyTaskGUI(SchedulerGUI scheduler, String GUIType, int taskRow) throws Exception {
        // Initialization of attributes
        super(GUIType.equals("Add") ? "Add Task" : "Edit Task");
        this.type = GUIType;
        this.schedulerGUI = scheduler;
        this.controller = new ModifyTaskController(this, schedulerGUI, type, taskRow);

        // Initialization of JComponents in the GUI
        this.enterTaskNameLabel = new JLabel("Enter Task Name Here: ");
        this.enterTaskName = new JTextField();
        this.enterTaskDescriptionLabel = new JLabel("Enter Task Description Here: ");
        this.enterTaskDescription = new JTextArea();
        this.scrollableEnterTaskDescription = new JScrollPane(enterTaskDescription);
        this.enterDateTimeLabel = new JLabel("Select a date/time for completion: ");
        this.chooseMonth = new JComboBox();
        this.chooseDate = new JComboBox();
        this.chooseHour = new JComboBox();
        this.chooseYear = new JComboBox();
        this.timeSymbol = new JLabel(":");
        this.chooseMinute = new JComboBox();
        this.typeLabel = new JLabel("Type:");
        this.taskTypes = new ButtonGroup();
        this.timedTask = new JRadioButton("Timed Task");
        this.taskWithDeadline = new JRadioButton("Task with Deadline");
        this.event = new JRadioButton("Event");
        this.notificationsLabel = new JLabel("Notifications:");
        this.notifications = new JCheckBox();
        this.deleteButton = new JGradientButton("Delete");
        this.updateButton = new JGradientButton("Update");
        this.addButton = new JGradientButton("Add");
        panel.setLayout(null);

        // Fills in the task details if a task is being edited
        if (type.equals("Edit")){
            controller.setTaskDetails(taskRow);
        }

        // Sets label for entering task name
        enterTaskNameLabel.setFont(smallFont);
        enterTaskNameLabel.setBounds(20, -60, 400, 200);
        panel.add(enterTaskNameLabel);

        // Allows user to enter task name
        enterTaskName.setBounds(20, 60, 335, 30);
        panel.add(enterTaskName);

        // Sets label for entering task description
        enterTaskDescriptionLabel.setFont(smallFont);
        enterTaskDescriptionLabel.setBounds(20, 20, 400, 200);
        panel.add(enterTaskDescriptionLabel);
        enterTaskDescription.setLineWrap(true);

        // Allows textbox to be scrollable
        scrollableEnterTaskDescription.setBounds(20, 145, 335, 180);
        scrollableEnterTaskDescription.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollableEnterTaskDescription);

        // Sets label for selecting date and time
        enterDateTimeLabel.setFont(smallFont);
        enterDateTimeLabel.setBounds(20, 260, 400, 200);
        panel.add(enterDateTimeLabel);

        // Allows user to choose a month
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for (String month : months) {
            chooseMonth.addItem(month);
        }
        chooseMonth.setBounds(20, 380, 100, 25);
        panel.add(chooseMonth);

        // Allows user to choose a date
        for (int i = 1; i <= 31; i++) {
            chooseDate.addItem(Integer.toString(i));
        }
        chooseDate.setBounds(140, 380, 100, 25);
        panel.add(chooseDate);

        // Allows user to choose the hour
        for (int i = 1; i <= 24; i++) {
            chooseHour.addItem(Integer.toString(i - 1));
        }
        chooseHour.setBounds(20, 410, 100, 25);
        panel.add(chooseHour);

        // Symbol that separates hours and minutes
        timeSymbol.setBounds(130, 410, 100, 25);
        panel.add(timeSymbol);

        // Allows user to choose the minute
        for (int i=-1; i<59; i++) {
            chooseMinute.addItem(Integer.toString(i + 1));
        }
        chooseMinute.setBounds(140, 410, 100, 25);
        panel.add(chooseMinute);

        // Allows user to choose the year
        for (int i = 2022; i <= 2122; i++) {
            chooseYear.addItem(Integer.toString(i));
        }
        chooseYear.setBounds(260, 380, 100, 25);
        panel.add(chooseYear);

        // Sets label for type of event
        typeLabel.setFont(smallFont);
        typeLabel.setBounds(20, 410, 100, 100);
        panel.add(typeLabel);

        // Checkboxes for type of event
        timedTask.setBounds(20, 480, 140, 25);
        panel.add(timedTask);
        taskWithDeadline.setBounds(20, 510, 140, 25);
        panel.add(taskWithDeadline);
        event.setBounds(20, 540, 140, 25);
        taskTypes.add(timedTask);
        taskTypes.add(taskWithDeadline);
        taskTypes.add(event);
        if (type.equals("Add")) {
            timedTask.setSelected(true);
        }
        panel.add(event);

        // Sets label for notifications
        notificationsLabel.setFont(smallFont);
        notificationsLabel.setBounds(190, 410, 200, 100);
        panel.add(notificationsLabel);

        // Checkbox for notifications
        notifications.setBounds(190, 480, 25, 25);
        panel.add(notifications);

        // Displays the delete and update buttons if a task is being edited
        if (type.equals("Edit")) {
            deleteButton.setFont(smallFont);
            deleteButton.setBounds(35, 585, 125, 50);
            panel.add(deleteButton);

            updateButton.setFont(smallFont);
            updateButton.setBounds(220, 520, 125, 50);
            panel.add(updateButton);
        }
        // Displays the add button if a task is being added
        else {
            addButton.setFont(smallFont);
            addButton.setBounds(35, 585, 125, 50);
            panel.add(addButton);
        }

        // Adds ActionListener objects to each button in the ModifyTaskGUI
        addButton.addActionListener(controller.getAddButtonAction());
        deleteButton.addActionListener(controller.getDeleteButtonAction());
        closeButton.addActionListener(controller.getCloseButtonAction());
        updateButton.addActionListener(controller.getUpdateButtonAction());
        chooseMonth.addActionListener(controller.getChooseMonthAction());
        chooseYear.addActionListener(controller.getChooseMonthAction());

        setVisible(true);
    }

    /**
     * Method which updates the selectable date numbers based on the month/year selected
     * @author Hadi
     */
    public void updatesDates() {
        ArrayList<String> months = new ArrayList<String>(List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"));
        chooseDate.removeAllItems();
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(String.valueOf(chooseYear.getSelectedItem())), 1 + months.indexOf(String.valueOf(chooseMonth.getSelectedItem())));
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            chooseDate.addItem(Integer.toString(i));
        }
    }

    /**
     * Method which returns the task name the user entered
     * @author Hadi
     * @return The task name
     */
    public String getTaskName() {
        return enterTaskName.getText();
    }

    /**
     * Method which sets a new task name into the JTextField
     * @author Hadi
     * @param taskName The new task name
     */
    public void setTaskName(String taskName) {
        enterTaskName.setText(taskName);
    }

    /**
     * Method which returns the task description the user entered
     * @author Hadi
     * @return The task description
     */
    public String getTaskDescription() {
        return enterTaskDescription.getText();
    }

    /**
     * Method which sets a new task description into the JTextArea
     * @author Hadi
     * @param taskDescription The new task description
     */
    public void setTaskDescription(String taskDescription) {
        enterTaskDescription.setText(taskDescription);
    }

    /**
     * Method which returns the deadline entered by the user for the task
     * @author Hadi
     * @return The deadline of the task
     */
    public Date getTaskDeadline() {
        // Retrieves the deadline information from the GUI
        String month = String.valueOf(chooseMonth.getSelectedItem());
        String day = String.valueOf(chooseDate.getSelectedItem());
        String hour = String.valueOf(chooseHour.getSelectedItem());
        String minute = String.valueOf(chooseMinute.getSelectedItem());
        String year = String.valueOf(chooseYear.getSelectedItem());

        // Returns a date object using the deadline information retrieved from the GUI
        return new Date(month + " " + day + " " + hour + ":" + minute + " EST " + year);
    }

    /**
     * Method which sets a new deadline for the task in the JComboBoxes
     * @author Hadi
     * @param deadline The new deadline
     */
    public void setTaskDeadline(Date deadline) {
        // Changes the values selected in the JComboBoxes based on the new deadline
        String[] deadlineDetails = String.valueOf(deadline).split(" ");
        chooseMonth.getModel().setSelectedItem(deadlineDetails[1]);
        chooseDate.getModel().setSelectedItem(deadlineDetails[2]);
        chooseHour.getModel().setSelectedItem(deadlineDetails[3].substring(0, 2));
        chooseMinute.getModel().setSelectedItem(deadlineDetails[3].substring(3, 5));
        chooseYear.getModel().setSelectedItem(deadlineDetails[5]);
    }

    /**
     * method which retrieves the task type the user selected
     * @author Hadi
     * @return The task type
     */
    public String getTaskType() {
        if (timedTask.isSelected()) {
            return "Timed task";
        }
        else if (taskWithDeadline.isSelected()) {
            return "Task with Deadline";
        }
        else {
            return "Event";
        }
    }

    /**
     * Method which sets a new task type into the GUI's JRadioButton group
     * @author Hadi
     * @param taskType The new task type
     */
    public void setTaskType(String taskType) {
        if (timedTask.equals("Timed task")) {
            timedTask.setSelected(true);
        }
        else if (taskType.equals("Task with Deadline")) {
            taskWithDeadline.setSelected(true);
        }
        else {
            event.setSelected(true);
        }
    }

    /**
     * Method which returns the notification setting the user selected
     * @author Hadi
     * @return The notification setting (True/False)
     */
    public boolean getTaskNotifications() {
        return notifications.isSelected();
    }

    /**
     * Method which sets a new setting for the notifications of the task
     * @author Hadi
     * @param notificationSetting The new setting for notifications (True/False)
     */
    public void setTaskNotifications(boolean notificationSetting) {
        notifications.setSelected(notificationSetting);
    }
}