/**
 * ZenTime | SmallGUI.java
 * Date Modified: January 24th, 2022
 * @author Hadi Naqvi
 */

package com.zentime;

import javax.swing.*;
import java.awt.*;

public abstract class SmallGUI extends JFrame {
    // Declaration of GUI and class attributes
    private Image icon;
    private JLabel title;
    protected JGradientButton closeButton;
    protected JPanel panel;
    protected Font smallFont = new Font("Tahoma", Font.PLAIN, 20),
            largeFont = new Font("Tahoma", Font.BOLD, 26),
            lightFont = new Font("Tahoma", Font.PLAIN, 14),
            headingFont = new Font("Tahoma", Font.BOLD, 18);

    /**
     * Constructor method for small GUIs which initializes and configures attributes and JComponents
     * @author Hadi
     * @param heading The name/title of the GUI
     */
    protected SmallGUI(String heading) {
        // Initializing attributes
        this.icon = Toolkit.getDefaultToolkit().getImage("assets\\Logo.png");
        this.title = new JLabel(heading, SwingConstants.CENTER);
        this.closeButton = new JGradientButton("Close");
        this.panel = new JPanel();

        // Configuring properties of JComponents
        title.setBounds(120, 25, 200, 25);
        title.setFont(largeFont);
        closeButton.setBounds(250, 665, 125, 50);
        closeButton.setFont(smallFont);
        panel.setBounds(30, 80, 375, 645);
        panel.setBackground(new Color(80, 150, 255));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Adding JComponents to the frame
        add(title);
        add(closeButton);
        add(panel);

        // Configuring Frame properties
        setTitle(heading);
        setIconImage(icon);
        setSize(450, 800);
        getContentPane().setBackground(new Color(120, 175, 255));
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
