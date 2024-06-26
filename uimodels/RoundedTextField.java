package uimodels;

import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {
    private int radius;

    public RoundedTextField(int radius) {
        super();
        this.radius = radius;
        setMargin(new Insets(10, 10, 10, 10)); // Add 10 pixels of margin on all sides
        setForeground(Color.BLACK); // Set the text color to black
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
    }
}