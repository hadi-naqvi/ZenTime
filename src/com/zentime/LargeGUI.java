/**
 * ZenTime | LargeGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public abstract class LargeGUI extends JFrame implements Runnable {
    // Declaration of GUI and class attributes
    private Image icon;
    private JLabel logo, title, digitalClock;
    private Thread thread;
    protected JLabel leftTitle, rightTitle;
    protected JPanel leftPanel, rightPanel;
    protected Font smallFont = new Font("Tahoma", Font.PLAIN, 20),
            largeFont = new Font("Tahoma", Font.BOLD, 28),
            lightFont = new Font("Tahoma", Font.PLAIN, 14),
            headingFont = new Font("Tahoma", Font.BOLD, 18);

    /**
     * Constructor method for large GUIs which initializes and configures attributes and JComponents
     * @author Hadi
     * @param heading The name/title of the GUI
     * @param leftTitleText The text displayed in the left panel's title
     * @param rightTitleText The text displayed in the right panel's title
     */
    protected LargeGUI(String heading, String leftTitleText, String rightTitleText) {
        // Initialization of attributes
        this.icon = Toolkit.getDefaultToolkit().getImage("assets\\Logo.png");
        this.logo = new JLabel(new ImageIcon("assets\\logo_small.png"));
        this.title = new JLabel("ZenTime | " + heading);
        this.digitalClock = new JLabel();
        this.thread = new Thread(this);
        this.thread.start();
        this.leftTitle = new JLabel(leftTitleText, SwingConstants.CENTER);
        this.rightTitle = new JLabel(rightTitleText, SwingConstants.CENTER);
        this.leftPanel = new JPanel();
        this.rightPanel = new JPanel();

        // Configuring properties of JComponents
        logo.setBounds(10, 10, 100, 100);
        title.setBounds(125, 35, 500, 50);
        digitalClock.setBounds(950, 35, 300, 50);
        title.setFont(largeFont);
        digitalClock.setFont(smallFont);
        leftTitle.setFont(largeFont);
        rightTitle.setFont(largeFont);
        leftPanel.setBackground(new Color(80, 150, 255));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightPanel.setBackground(new Color(80, 150, 255));
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Adding JComponents to the frame
        add(logo);
        add(title);
        add(digitalClock);
        add(leftPanel);
        add(rightPanel);

        // Configuring Frame properties
        setTitle("ZenTime | " + heading);
        setIconImage(icon);
        setSize(1280, 720);
        getContentPane().setBackground(new Color(120, 175, 255));
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * This method continuously updates the digital clock every second
     * @author Hadi
     */
    @Override
    public void run() {
        try {
            while (true) {
                // Creates a calendar instance and sets the digital clock to the local date/time every second
                Calendar calendar = Calendar.getInstance();
                digitalClock.setText("" + calendar.getTime());
                thread.sleep(1000);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}