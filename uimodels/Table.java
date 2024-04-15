package uimodels;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;

public class Table extends JTable {

    public Table(TableModel model) {
        super(model);
        setOpaque(false);
        setBackground(Color.PINK);
        setFont(new Font("Century Gothic", Font.PLAIN, 11));

        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new RoundedHeaderRenderer());


        //center the text in the table, idk how but thank gd it works
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
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

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);
        component.setBackground(Color.PINK.brighter());
        return component;
    }
}