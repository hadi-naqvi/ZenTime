/**
 * ZenTime | ModifyTaskController.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class ModifyTaskController extends EventHandler {
    // Declaration of controller attributes
    private ModifyTaskGUI GUI;
    private SchedulerGUI schedulerGUI;
    private int taskRow;
    private String type;
    private ActionListener addButtonAction, deleteButtonAction, updateButtonAction, chooseMonthAction;

    /**
     * Constructor method for ModifyTaskGUI's controller
     * @author Hadi
     * @param modifyTask The Add/Edit task window's GUI object
     * @param scheduler The Scheduler window's GUI object
     * @param controllerType The type of window (Add or Edit)
     * @param row The row number of the selected task
     * @throws Exception An Exception caused by the super constructor method
     */
    protected ModifyTaskController(ModifyTaskGUI modifyTask, SchedulerGUI scheduler, String controllerType, int row) throws Exception {
        // Initialization of class attributes
        super();
        this.GUI = modifyTask;
        this.schedulerGUI = scheduler;
        this.taskRow = row;
        this.type = controllerType;

        // Initialization of ActionListener objects
        this.addButtonAction = modify();
        this.deleteButtonAction = delete();
        this.updateButtonAction = modify();
        this.closeButtonAction = close(GUI);
        this.chooseMonthAction = populateDates();
    }

    /**
     * Method which validates task details and provides an error message for invalid tasks
     * @author Hadi
     * @param name The name of the task being validated
     * @param description The description of the task being validated
     * @param deadline The deadline of the task being validated
     * @return If the task is valid, or an error message if it's invalid
     * @throws IOException An exception caused by file I/O
     */
    private String validateTask(String name, String description, Date deadline) throws IOException {
        // Initialization of validity of task and error message
        boolean isValid = true;
        String errorMessage = "Error: Task could not be added/updated for the following reason(s):";

        // Checks if the name or description contains the symbol "~" which is impermissible
        if (name.contains("~") || description.contains("~")) {
            isValid = false;
            errorMessage += "\n- You may not enter the symbol '~' in your task name/description";
        }

        // Checks if the name or description have not been filled (They must be filled out)
        if (name.equals("") || description.equals("")) {
            isValid = false;
            errorMessage += "\n- You have not filled in all the required fields";
        }

        // Checks if an existing task has the same name (Tasks cannot have the same name)
        importItems("task");
        if (type.equals("Add")) {
            for (Task task : super.getTasks()) {
                if (name.equals(task.getName())) {
                    isValid = false;
                    errorMessage += "\n- An existing task with the same name already exists";
                    break;
                }
            }
        }

        // Checks if the task's deadline is in the past (Cannot create a task with a deadline in the past)
        if (deadline.before(new Date(String.valueOf(Calendar.getInstance().getTime())))){
            isValid = false;
            errorMessage += "\n- You cannot add a task with a deadline in the past";
        }

        // Returns the result of the validation/error message
        return (isValid) ? "Valid" : errorMessage;
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the add button
     * @author Hadi
     * @return The add button's ActionListener
     */
    private ActionListener modify() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Creates a confirm dialog, which asks the user to confirm whether they would like to add/edit a task
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + type + " this task?", "Confirm Task",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    // If the user confirms they would like to add/edit their task, it is validated and then added/edited
                    if (confirmation == 0) {
                        // Validates the task
                        String taskValidation = validateTask(GUI.getTaskName(), GUI.getTaskDescription(), GUI.getTaskDeadline());

                        // If the task is valid, it is added/edited into the system
                        if (taskValidation.equals("Valid")) {
                            importItems("task");

                            // A new task is added to the system if the user is adding a task
                            if (type.equals("Add")) {
                                addNewTask(new Task(GUI.getTaskName(), GUI.getTaskDescription(), GUI.getTaskType(),
                                        new Date(String.valueOf(Calendar.getInstance().getTime())), GUI.getTaskDeadline(),
                                        new Date("Thu Jan 1 23:59:00 EST 2122"), new Date(GUI.getTaskDeadline().getTime() - 300000), GUI.getTaskNotifications(), "Incomplete"));
                            }
                            // The existing task being edited is updated in the system with its new details
                            else {
                                Task oldTask = getTask(taskRow);
                                updateTask(taskRow, new Task(GUI.getTaskName(), GUI.getTaskDescription(), GUI.getTaskType(), oldTask.getDateAdded(), GUI.getTaskDeadline(),
                                        oldTask.getDateCompleted(), new Date(GUI.getTaskDeadline().getTime() - 300000), GUI.getTaskNotifications(), oldTask.getStatus()));
                            }

                            // The system updates the .csv file storing the list of tasks locally with the new changes
                            updateItems("task");

                            // The table in the scheduler window is cleared and repopulated to reflect the new changes
                            schedulerGUI.clearTasks();
                            for (Task task : getTasks()) {
                                schedulerGUI.addRow(new Object[]{String.valueOf(task.getDeadline()).substring(4, 16), task.getName(), task.getStatus()});
                            }

                            // The ModifyTask window/GUI is closed and the user is given a message telling them their task has been added/edited
                            GUI.dispose();
                            JOptionPane.showMessageDialog(null, "Successfully " + type + "ed task!");
                        }
                        // If the user's task details are invalid, they are shown an error message explaining why
                        else {
                            JOptionPane.showMessageDialog(null, taskValidation, "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        };
    }

    /**
     * Method which creates the ActionListener/functionality for the delete button
     * @author Hadi
     * @return The delete button's ActionListener
     */
    private ActionListener delete() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Creates a confirm dialog, which asks the user to confirm whether they would like to delete the selected task
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this task?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    // If the user confirms they would like to delete the task, it is then deleted
                    if (confirmation == 0) {
                        // The task is removed from the system's list of tasks and the .csv file storing the list of tasks is updated
                        importItems("task");
                        removeTask(getTask(taskRow));
                        updateItems("task");

                        // The table in the scheduler window is cleared and repopulated to reflect the new changes
                        schedulerGUI.clearTasks();
                        for (Task task : getTasks()) {
                            schedulerGUI.addRow(new Object[]{String.valueOf(task.getDeadline()).substring(4, 16), task.getName(), task.getStatus()});
                        }

                        // Closes the window and displays the result of the deletion to the user in a popup dialog
                        GUI.dispose();
                        JOptionPane.showMessageDialog(null, "Successfully deleted task!");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which populates the JComboBox for selecting a date with day numbers depending on the year/month
     * @author Hadi
     * @return The JComboBox's ActionListeners
     */
    private ActionListener populateDates() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.updatesDates();
            }
        };
    }

    /**
     * Method which fetches and sets the task details of the task being edited into the window/GUI
     * @author Hadi
     * @param taskNumber The index of the task being edited in the system's list of tasks
     * @throws IOException An exception caused by file I/O
     */
    public void setTaskDetails(int taskNumber) throws IOException {
        // Retrieves the task being edited from the system's list of tasks
        importItems("task");
        Task task = getTask(taskNumber);

        // Sets the task details into the GUI components
        GUI.setTaskName(task.getName());
        GUI.setTaskDescription(task.getDescription());
        GUI.setTaskDeadline(task.getDeadline());
        GUI.setTaskType(task.getType());
        GUI.setTaskNotifications(task.hasNotifications());
    }

    /**
     * Method which returns the add button's ActionListener
     * @author Hadi
     * @return The add button's ActionListener
     */
    public ActionListener getAddButtonAction() {
        return addButtonAction;
    }

    /**
     * Method which returns the add button's ActionListener
     * @author Hadi
     * @return The delete button's ActionListener
     */
    public ActionListener getDeleteButtonAction() {
        return deleteButtonAction;
    }

    /**
     * Method which returns the update button's ActionListener
     * @author Hadi
     * @return The update button's ActionListener
     */
    public ActionListener getUpdateButtonAction() {
        return updateButtonAction;
    }

    /**
     * Method which returns the JComboBox's ActionListener
     * @author Hadi
     * @return The JComboBox's ActionListener
     */
    public ActionListener getChooseMonthAction() {
        return chooseMonthAction;
    }
}
