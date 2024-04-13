package ui;

import structure.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import structure.ProductGroup;
import structure.Product;

public class Items extends JPanel {
    private JTextArea productListTextArea;
    private JPanel productPanel;
    private JComboBox<String> groupComboBox;
    private ArrayList<ProductGroup> existingGroups;

    public Items() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("ui.Groups of goods");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Кнопки
        JButton searchButton = new JButton("Search");
        JButton addGroupButton = new JButton("Add Item");
        JButton deleteGroupButton = new JButton("Delete Item");

        // Додавання кнопок на панель
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(addGroupButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteGroupButton);

        // Створення ComboBox перед викликом методу updateGroupComboBox()
        groupComboBox = new JComboBox<>();
        groupComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 16));

        // Додавання ComboBox на панель кнопок
        buttonPanel.add(groupComboBox);

        // Додавання панелі кнопок на панель
        add(buttonPanel, BorderLayout.SOUTH);

        // TextArea для виведення списку продуктів
        productListTextArea = new JTextArea();
        productListTextArea.setEditable(false);
        productListTextArea.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(productListTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Панель для виведення продуктів
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane productScrollPane = new JScrollPane(productPanel);
        add(productScrollPane, BorderLayout.EAST);

        // Додавання дій до кнопок
        addGroupButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = (String) groupComboBox.getSelectedItem();
                if (selectedGroup != null) {
                    boolean validInput = false;
                    String name = "";
                    String description = "";
                    String manufacturer = "";
                    int quantity = 0;
                    double price = 0.0;

                    while (!validInput) {
                        name = JOptionPane.showInputDialog(null, "Enter the name of the product:");
                        if (name != null) {
                            description = JOptionPane.showInputDialog(null, "Enter the description of the product:");
                            if (description != null) {
                                manufacturer = JOptionPane.showInputDialog(null, "Enter the manufacturer of the product:");
                                if (manufacturer != null) {
                                    String quantityStr = JOptionPane.showInputDialog(null, "Enter the quantity:");
                                    if (quantityStr != null) {
                                        try {
                                            quantity = Integer.parseInt(quantityStr);
                                            String priceStr = JOptionPane.showInputDialog(null, "Enter the price:");
                                            if (priceStr != null) {
                                                try {
                                                    price = Double.parseDouble(priceStr);
                                                    validInput = true;
                                                } catch (NumberFormatException ex) {
                                                    JOptionPane.showMessageDialog(null, "Invalid price! Please enter a valid price.", "Error", JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                        } catch (NumberFormatException ex) {
                                            JOptionPane.showMessageDialog(null, "Invalid quantity! Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    productListTextArea.append("Group: " + selectedGroup + "\n");
                    productListTextArea.append("Name: " + name + "\n");
                    productListTextArea.append("Description: " + description + "\n");
                    productListTextArea.append("Manufacturer: " + manufacturer + "\n");
                    productListTextArea.append("Quantity: " + quantity + "\n");
                    productListTextArea.append("Price: " + price + "\n\n");

                    JLabel productLabel = new JLabel("<html><b>Group:</b> " + selectedGroup + ", <b>Name:</b> " + name + ", <b>Quantity:</b> " + quantity + ", <b>Price:</b> " + price + "</html>");
                    productLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                    productPanel.add(productLabel);
                    productPanel.revalidate();
                    productPanel.repaint();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog("Enter group name to search");
                for (ProductGroup group : existingGroups) {
                    if (group.getName().equals(search)) {
                        groupComboBox.setSelectedItem(group.getName());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Group not found");
            }
        });

        deleteGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = (String) groupComboBox.getSelectedItem();
                if (selectedGroup != null) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete group \"" + selectedGroup + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Delete group logic
                        for (ProductGroup group : existingGroups) {
                            if (group.getName().equals(selectedGroup)) {
                                ProductGroup.deleteGroup(group);
                                break;
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Group \"" + selectedGroup + "\" deleted successfully.");
                        updateGroupComboBox(); // Оновлення ComboBox після видалення
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No group selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateGroupComboBox(); // Оновлення ComboBox
    }

    // Оновлення ComboBox на основі актуальних даних
    private void updateGroupComboBox() {
        existingGroups = ProductGroup.groups; // Оновлення існуючих груп
        ArrayList<String> groupNames = new ArrayList<>();
        for (ProductGroup group : existingGroups) {
            groupNames.add(group.getName());
        }
        groupComboBox.setModel(new DefaultComboBoxModel<>(groupNames.toArray(new String[0])));
    }
}
