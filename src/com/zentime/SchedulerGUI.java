/**
 * ZenTime | SchedulerGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SchedulerGUI extends LargeGUI {
    // Declaration of GUI and class attributes
    private SchedulerController controller;
    private DefaultTableModel tableModel;
    private JTable schedule;
    private JScrollPane scrollBar;
    private JGradientButton addTaskButton, viewDetailsButton, editTaskButton, progressReportsButton, remindersButton, helpButton, exitButton;

    /**
     * Constructor method which initializes attributes and JComponents of the GUI
     * @author Hadi
     * @throws Exception An exception caused by super constructor method
     */
    public SchedulerGUI() throws Exception {
        // Initialization of GUI/class attributes
        super("Scheduler", "Schedule", "Extras");
        this.controller = new SchedulerController(this);
        this.tableModel = new DefaultTableModel();
        this.schedule = new JTable(tableModel);
        this.scrollBar = new JScrollPane(schedule);
        this.addTaskButton = new JGradientButton("Add Task");
        this.viewDetailsButton = new JGradientButton("View Details");
        this.editTaskButton = new JGradientButton("Edit Task");
        this.progressReportsButton = new JGradientButton("Progress Reports");
        this.remindersButton = new JGradientButton("Reminders");
        this.helpButton = new JGradientButton("Help");
        this.exitButton = new JGradientButton("Exit");

        // Configures the left panel
        leftPanel.setLayout(null);
        leftPanel.setBounds(25, 125, 850, 515);
        leftTitle.setBounds(360, 6, 129, 34);
        leftPanel.add(leftTitle);

        // Configures the JTable and JScrollPane
        schedule.setDefaultEditor(Object.class, null);
        schedule.getTableHeader().setReorderingAllowed(false);
        schedule.getTableHeader().setResizingAllowed(false);
        schedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        schedule.setRowHeight(30);
        schedule.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        schedule.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tableModel.addColumn("Deadline");
        tableModel.addColumn("Task");
        tableModel.addColumn("Status");
        schedule.getColumnModel().getColumn(0).setPreferredWidth(175);
        schedule.getColumnModel().getColumn(1).setPreferredWidth(390);
        schedule.getColumnModel().getColumn(2).setPreferredWidth(175);
        scrollBar.setBounds(50, 60, 760, 350);
        controller.populateSchedule();
        leftPanel.add(scrollBar);

        // Configures the positions of the left panel's buttons on-screen
        addTaskButton.setBounds(50, 450, 150, 50);
        viewDetailsButton.setBounds(350, 450, 150, 50);
        editTaskButton.setBounds(650, 450, 150, 50);

        // Configures and adds every button in the left panel to the left panel
        addTaskButton.setFont(smallFont);
        leftPanel.add(addTaskButton);
        viewDetailsButton.setFont(smallFont);
        leftPanel.add(viewDetailsButton);
        editTaskButton.setFont(smallFont);
        leftPanel.add(editTaskButton);

        // Configures the right panel
        rightPanel.setBounds(925, 125, 325, 515);
        rightPanel.setLayout(new GridLayout(5, 1, 0, 30));
        rightPanel.add(rightTitle);

        // Configures and adds every button in the right panel to the right panel
        progressReportsButton.setFont(smallFont);
        rightPanel.add(progressReportsButton);
        remindersButton.setFont(smallFont);
        rightPanel.add(remindersButton);
        helpButton.setFont(smallFont);
        rightPanel.add(helpButton);
        exitButton.setFont(smallFont);
        rightPanel.add(exitButton);

        // Adds action listener objects to each button in the Scheduler GUI
        addTaskButton.addActionListener(controller.getAddTaskAction());
        viewDetailsButton.addActionListener(controller.getViewDetailsAction());
        editTaskButton.addActionListener(controller.getEditTaskAction());
        progressReportsButton.addActionListener(controller.getProgressReportsAction());
        remindersButton.addActionListener(controller.getRemindersAction());
        helpButton.addActionListener(controller.getHelpButtonAction());
        exitButton.addActionListener(controller.getExitButtonAction());

        setVisible(true);
    }

    /**
     * This method adds a new task/row to the schedule JTable
     * @author Hadi
     * @param row The task/row being added to the JTable
     */
    public void addRow(Object[] row) {
        DefaultTableModel table = (DefaultTableModel) schedule.getModel();
        table.addRow(row);
    }

    /**
     * This method clears the Schedule JTable
     * @author Hadi
     */
    public void clearTasks() {
        DefaultTableModel table = (DefaultTableModel) schedule.getModel();
        table.setRowCount(0);
    }

    /**
     * Method which returns the selected row in the schedule JTable
     * @author Hadi
     * @return The selected row
     */
    public int getSelectedRow() {
        return schedule.getSelectedRow();
    }
}