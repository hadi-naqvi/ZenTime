/**
 * ZenTime | SchedulerController.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SchedulerController extends EventHandler {
    // Declaration of controller attributes
    private SchedulerGUI GUI;
    private ActionListener addTaskAction, viewDetailsAction, editTaskAction, progressReportsAction,
            remindersButtonAction, helpButtonAction, exitButtonAction;

    /**
     * Constructor method for scheduler GUIs controller class
     * @author Hadi
     * @param scheduler The instance of the Scheduler GUI
     * @throws Exception An exception caused by super constructor
     */
    public SchedulerController(SchedulerGUI scheduler) throws Exception {
        // Initialization of controller attributes
        super();
        this.GUI = scheduler;
        this.addTaskAction = addTask();
        this.viewDetailsAction = viewDetails();
        this.editTaskAction = editTask();
        this.progressReportsAction = openAnalytics();
        this.remindersButtonAction = openReminders();
        this.helpButtonAction = openHelpDialog();
        this.exitButtonAction = exit();
    }

    /**
     * This method populates the Schedule's JTable with the system's tasks
     * @author Hadi
     */
    public void populateSchedule() throws IOException {
        importItems("task");
        List<Task> tasks = getTasks();
        for (Task task : tasks) {
            GUI.addRow(new Object[]{String.valueOf(task.getDeadline()).substring(4, 16), task.getName(), task.getStatus()});
        }
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the add task button
     * @author Hadi
     * @return Add Task button's ActionListener
     */
    private ActionListener addTask() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ModifyTaskGUI(GUI,"Add", -1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the view details button
     * @author Hadi
     * @return View Detail button's ActionListener
     */
    private ActionListener viewDetails() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (GUI.getSelectedRow() != -1) {
                        new TaskDetailsGUI(GUI, GUI.getSelectedRow());
                    }
                    else {
                        // Prompts the user to select a task from the schedule before attempting to view task details
                        JOptionPane.showMessageDialog(new JFrame(),"Please select a task from the schedule to view.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the edit task button
     * @author Hadi
     * @return Edit Task button's ActionListener
     */
    private ActionListener editTask() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (GUI.getSelectedRow() != -1) {
                        new ModifyTaskGUI(GUI, "Edit", GUI.getSelectedRow());
                    }
                    else {
                        // Prompts the user to select a task from the schedule before attempting to edit the task
                        JOptionPane.showMessageDialog(new JFrame(),"Please select a task from the schedule to edit.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the Progress Reports button
     * @author Hadi
     * @return Progress Reports button's ActionListener
     */
    private ActionListener openAnalytics() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ProgressGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the Reminders button
     * @author Hadi
     * @return Reminders button's ActionListener
     */
    private ActionListener openReminders() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new RemindersGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    /**
     * Method which creates the ActionListener object for the Help button
     * @author Hadi
     * @return The ActionListener for the help button
     */
    private ActionListener openHelpDialog() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(),"Click the add task button to add tasks.\n" +
                        "Click the view task button to view a task's details.\n" +
                        "Click the edit task button to edit or delete tasks.\n" +
                        "Click the Progress Reports button to access analytics and progress data\n" +
                        "Click the Reminders button to access the program's reminders system\n" +
                        "\nWhat are different Task types?\n" +
                        "Timed Task: A task which is timed (Still has a deadline)\n" +
                        "Task with Deadline: A task with a deadline (Is not timed)\n" +
                        "Event: Any item in your schedule which isn't a task");
            }
        };
    }

    /**
     * Method which creates and returns the ActionListener/functionality for the exit button
     * @author Hadi
     * @return Exit button's ActionListener
     */
    private ActionListener exit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
    }

    /**
     * Method which returns the add task button's ActionListener object
     * @author Hadi
     * @return The add task button's ActionListener object
     */
    public ActionListener getAddTaskAction() {
        return addTaskAction;
    }

    /**
     * Method which returns the view detail button's ActionListener object
     * @author Hadi
     * @return The view details button's ActionListener object
     */
    public ActionListener getViewDetailsAction() {
        return viewDetailsAction;
    }

    /**
     * Method which returns the edit task button's ActionListener object
     * @author Hadi
     * @return The edit task button's ActionListener object
     */
    public ActionListener getEditTaskAction() {
        return editTaskAction;
    }

    /**
     * Method which returns the progress reports button's ActionListener object
     * @author Hadi
     * @return The progress report button's ActionListener object
     */
    public ActionListener getProgressReportsAction() {
        return progressReportsAction;
    }

    /**
     * Method which returns the Reminders button's ActionListener object
     * @author Hadi
     * @return The Reminders button's ActionListener object
     */
    public ActionListener getRemindersAction() {
        return remindersButtonAction;
    }

    /**
     * Method which returns the help button's ActionListener object
     * @author Hadi
     * @return The help button's ActionListener object
     */
    public ActionListener getHelpButtonAction() {
        return helpButtonAction;
    }

    /**
     * Method which returns the exit button's ActionListener object
     * @author Hadi
     * @return The exit button's ActionListener object
     */
    public ActionListener getExitButtonAction() {
        return exitButtonAction;
    }
}
