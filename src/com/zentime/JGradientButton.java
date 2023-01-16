/**
 * [ICS4U] ZenTime | JGradientButton.java
 * Date: January 24th, 2022
 * @author Affan, Hadi, Marlon, & Oscar
 * Teacher: Mr. Ho
 */

package com.zentime;

import javax.swing.*;
import java.awt.*;

public class JGradientButton extends JButton {
    /**
     * Constructor method which creates a JButton object
     * @param buttonName The name/label of the button
     * @author Hadi
     */
    protected JGradientButton(String buttonName) {
        super(buttonName);
        setContentAreaFilled(false);
    }

    /**
     * This method overrides the default button skin with a new Orange gradient painted skin
     * @param g Graphics context for the JButton
     * @author Hadi
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D orangeSkin = (Graphics2D)g.create();

        // Top-to-middle gradient (Bright Orange --> Light Orange)
        orangeSkin.setPaint(new GradientPaint(
                new Point(0, 0),
                new Color(255, 100, 0),
                new Point(0, getHeight() / 3),
                new Color(255, 150, 50)));
        orangeSkin.fillRect(0, 0, getWidth(), getHeight() / 3);

        // Middle-to-bottom gradient (Light Orange --> Bright Orange)
        orangeSkin.setPaint(new GradientPaint(
                new Point(0, getHeight()/3),
                new Color(255, 150, 50),
                new Point(0, getHeight()),
                new Color(255, 100, 0)));
        orangeSkin.fillRect(0, getHeight() / 3, getWidth(), getHeight());
        orangeSkin.dispose();

        // Paints the JButton with the new orange skin
        super.paintComponent(g);
    }
}
