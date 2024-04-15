 package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import structure.ProductGroup;
import structure.Product;

 public class FactoryUI extends JFrame {
    public FactoryUI() {
        setSize(540, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.getHSBColor(10, 35,270)); // Replace with your preferred color

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        Groups tab1 = new Groups();
        ArrayList<ProductGroup> existingGroups = ProductGroup.getExistingGroups();
        Items tab2 = new Items(existingGroups);
        Statistics tab3 = new Statistics();

        tab1.setBackground(Color.getHSBColor(2.0f/100, 4.0f/100, 98.0f/100));
        tab2.setBackground(Color.getHSBColor(2.0f/100, 4.0f/100, 98.0f/100));
        tab3.setBackground(Color.getHSBColor(2.0f/100, 4.0f/100, 98.0f/100));

        tabbedPane.addTab("Groups of items", tab1);
        tabbedPane.addTab("Items", tab2);
        tabbedPane.addTab("Statistics", tab3);

        tabbedPane.setBackgroundAt(0, Color.WHITE); // Set the background color of the first tab to red
        tabbedPane.setBackgroundAt(1,   Color.WHITE); // Set the background color of the second tab to green
        tabbedPane.setBackgroundAt(2,  Color.WHITE); // Set the background color of the third tab to blue

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new FactoryUI();
        ProductGroup.loadGroupsFromFile();
        Product.loadItemsFromFile();
    }
}