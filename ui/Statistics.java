package ui;

import structure.Product;
import structure.ProductGroup;
import uimodels.RoundedButton;
import uimodels.RoundedComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import ui.Items;

import static ui.Items.existingGroups;

public class Statistics extends JPanel {
    private static RoundedComboBox groupComboBox;
    private static RoundedComboBox itemComboBox;

    public Statistics() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Statistics");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Вирівнювання тексту по центру
        add(titleLabel, BorderLayout.NORTH);

        JPanel statisticsPanel = new JPanel(new GridBagLayout());
        statisticsPanel.setBackground((Color.getHSBColor(2.0f/100, 4.0f/100, 98.0f/100)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label "Price"
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        statisticsPanel.add(priceLabel, gbc);

        RoundedButton calculateButton = new RoundedButton("Calculate");
        calculateButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(calculateButton, gbc);


        JLabel totalCostLabel = new JLabel("Total cost of goods:");
        totalCostLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        statisticsPanel.add(totalCostLabel, gbc);
        double totalPrice = 0;
        JLabel totalPriceLabel = new JLabel("$" + totalPrice);
        totalPriceLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(totalPriceLabel, gbc);

        JLabel selectGroupLabel = new JLabel("Select group:");
        selectGroupLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        statisticsPanel.add(selectGroupLabel, gbc);

        // Create a new RoundedComboBox for the list of groups
        groupComboBox = new RoundedComboBox();
        groupComboBox.setPreferredSize(new Dimension(200, 30));
        // Populate the combo box with the list of groups


        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(groupComboBox, gbc);


        JLabel selectItemLabel = new JLabel("Select item:");
        selectItemLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        statisticsPanel.add(selectItemLabel, gbc);


        itemComboBox = new RoundedComboBox();
        itemComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(itemComboBox, gbc);
        groupComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductGroup selectedGroup = ProductGroup.findGroupByName((String) groupComboBox.getSelectedItem());
                updateItemComboBox(selectedGroup);
            }
        });

        add(statisticsPanel, BorderLayout.CENTER);
    }

    public static void updateGroupComboBox() {
        ArrayList<String> groupNames = new ArrayList();
        groupComboBox.removeAllItems();
        Iterator var1 = ProductGroup.groups.iterator();
        while(var1.hasNext()) {
            ProductGroup group = (ProductGroup)var1.next();
            groupNames.add(group.getName());
        }

        groupComboBox.setModel(new DefaultComboBoxModel((String[])groupNames.toArray(new String[0])));
    }
    public static void updateItemComboBox(ProductGroup selectedGroup) {
        ArrayList<String> itemsNames = new ArrayList();
        itemComboBox.removeAllItems();
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : Product.products) {
            if (product.getGroup().equals(selectedGroup)) {
                products.add(product);
            }
        }
        Iterator var1 = products.iterator();
        while (var1.hasNext()) {
            Product product = (Product) var1.next();
            itemsNames.add(product.getName());
        }

        itemComboBox.setModel(new DefaultComboBoxModel((String[])itemsNames.toArray(new String[0])));
    }
}
