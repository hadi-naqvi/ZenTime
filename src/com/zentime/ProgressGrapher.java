/**
 * ZenTime | ProgressGrapher.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ProgressGrapher extends ApplicationFrame {
    /**
     * Constructor method for ProgressGrapher class
     * @author Hadi
     */
    public ProgressGrapher() {
        super("ZenTime | Data Charts");
    }

    /**
     * Method which generates a graph of progress data
     * @author Hadi
     * @param completeTasks The number of completed tasks
     * @param inProgressTasks The number of in progress tasks
     * @param incompleteTasks The number of incomplete tasks
     * @return The progress data chart
     */
    private static ChartPanel generateProgressChart(int completeTasks, int inProgressTasks, int incompleteTasks) {
        // Initializes the dataset for the
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue( "Completed Tasks" , new Double(completeTasks));
        dataset.setValue( "In Progress Tasks" , new Double(inProgressTasks));
        dataset.setValue( "Incomplete Tasks" , new Double(incompleteTasks));

        // Creates the chart using the progress dataset
        JFreeChart chart = ChartFactory.createPieChart("Task Progress", dataset, true, true, false);

        return new ChartPanel(chart);
    }

    /**
     * Method which generates a graph of completion times data
     * @author Hadi
     * @param deadlines Deadlines
     * @param estimatedCompletionTimes Estimated completion times
     * @return The completion times chart
     */
    private static JFreeChart generateCompletionTimesChart(String[] deadlines, String[] estimatedCompletionTimes) {
        // Creates the dataset for the completion times chart
        final String deadline = "Deadline";
        final String estimated = "Estimated Completion";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Iterates through each task's deadline and estimated completion time and adds it to the bar graph
        for (int i = 0; i < deadlines.length; i++) {
            dataset.addValue(Integer.parseInt(deadlines[i]), deadline, "Task #" + String.valueOf(i + 1));
            dataset.addValue(Integer.parseInt(estimatedCompletionTimes[i]), estimated, "Task #" + String.valueOf(i + 1));
        }

        // Creates a bar graph which displays the completion times data
        JFreeChart barChart = ChartFactory.createBarChart("ZenTime | Data  Charts","Tasks",
                "# of hours from now", dataset, PlotOrientation.VERTICAL,true,true,false);

        return barChart;
    }

    /**
     * Method which generates a graph of completion statistics data
     * @author Hadi
     * @param early The number of early task completions
     * @param late The number of late task completions
     * @return The completion statistics data chart
     */
    private static ChartPanel generateCompletionStatisticsChart(int early, int late) {
        // Initializes the dataset for the completion statistics
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue( "Tasks Completed Early" , new Double(early));
        dataset.setValue( "Tasks Completed Late" , new Double(late));

        // Creates the chart using the progress dataset
        JFreeChart chart = ChartFactory.createPieChart("Completion Statistics", dataset, true, true, false);

        return new ChartPanel(chart);
    }

    /**
     * Method which generates and displays the chart of task progress data
     * @author Hadi
     * @param completeTasks The number of completed tasks
     * @param inProgressTasks The number of tasks in progress
     * @param incompleteTasks The number of incomplete tasks
     */
    public void graphProgress(int completeTasks, int inProgressTasks, int incompleteTasks) {
        setContentPane(generateProgressChart(completeTasks, inProgressTasks, incompleteTasks));
        setSize(560,367);
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    /**
     * Method which generates and displays the chart of completion statistics data
     * @author Hadi
     * @param early The number of early task completions
     * @param late The number of late task completions
     */
    public void graphCompletionStatistics(int early, int late) {
        setContentPane(generateCompletionStatisticsChart(early, late));
        setSize(560,367);
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    /**
     * Method which generates and displays time completion data
     * @author Hadi
     * @param deadlines Deadlines
     * @param estimatedCompletionTimes Estimated completion times
     */
    public void graphCompletionTimes(String[] deadlines, String[] estimatedCompletionTimes) {
        ChartPanel chartPanel = new ChartPanel(generateCompletionTimesChart(deadlines, estimatedCompletionTimes));
        chartPanel.setPreferredSize(new java.awt.Dimension(800,600));
        setContentPane(chartPanel);
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }
}