package uimodels;

import javax.swing.*;
import java.awt.*;

public class RoundedComboBox extends JComboBox {

    public RoundedComboBox() {
        setOpaque(false);
        setBackground(Color.WHITE);
        setFont(new Font("Century Gothic", Font.PLAIN, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.getHSBColor(10, 35,230));
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
    }
}