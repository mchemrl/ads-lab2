package uimodels;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    public RoundedButton(String label) {
        super(label);

        setContentAreaFilled(false);
        setFocusPainted(false);

        setBackground(Color.WHITE);

        setForeground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.getHSBColor(10, 35,230));
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
    }
}