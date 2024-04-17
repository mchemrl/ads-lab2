package ui;

import structure.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.HashSet;

import structure.Product;
import uimodels.RoundedButton;
import uimodels.RoundedComboBox;
import uimodels.Table;
import uimodels.RoundedTextField;


public class Items extends JPanel {
    private JTextArea productListTextArea;
    private JPanel productPanel;
    private static JComboBox<String> groupComboBox;
    public static ArrayList<ProductGroup> existingGroups;
    private static Table productTable;

    public Items(ArrayList<ProductGroup> existingGroups) {
        setLayout(new BorderLayout());
        this.existingGroups = existingGroups;
        JLabel titleLabel = new JLabel("Items");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        RoundedButton searchButton = new RoundedButton("Search");
        RoundedButton addItemButton = new RoundedButton("Add");
        RoundedButton deleteItemButton = new RoundedButton("Delete");
        RoundedButton editItemButton = new RoundedButton("Edit");
        RoundedButton viewButton = new RoundedButton("View all");
        RoundedButton changeQuantityButton = new RoundedButton("Change quantity");


        buttonPanel.add(searchButton);
        buttonPanel.add(addItemButton);
        buttonPanel.add(deleteItemButton);
        buttonPanel.add(editItemButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(changeQuantityButton);

        add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"Group", "Name", "Description", "Manufacturer", "Quantity", "Price"};
        Object[][] data = getProductData();

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productTable = new Table(model);
        productTable.setDefaultEditor(Object.class, null);
        productTable.setFillsViewportHeight(true);
        productTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane productScrollPane = new JScrollPane(productPanel);
        add(productScrollPane, BorderLayout.EAST);

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                model.setRowCount(0); // Clear the table
                HashSet<String> productNames = new HashSet<>();

                for (Product product : Product.products) {
                    if (!productNames.contains(product.getName())) {
                        productNames.add(product.getName());
                        model.addRow(new Object[]{product.getGroup(), product.getName(), product.getDescription(), product.getManufacturer(), product.getQuantity(), product.getPrice()});
                    }
                }
            }
        });

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.WHITE);
                panel.setLayout(new GridLayout(15, 1)); // Increase the grid size to accommodate the new combo box
                panel.setPreferredSize(new Dimension(300, 580));
                RoundedTextField nameField = new RoundedTextField(15);
                RoundedTextField descriptionField = new RoundedTextField(15);
                RoundedTextField manufacturerField = new RoundedTextField(15);
                RoundedTextField quantityField = new RoundedTextField(15);
                RoundedTextField priceField = new RoundedTextField(15);

                // Create a new RoundedComboBox for the list of groups
                RoundedComboBox groupComboBox = new RoundedComboBox();
                // Populate the combo box with the list of groups
                for (ProductGroup group : existingGroups) {
                    groupComboBox.addItem(group.getName());
                }

                GridBagConstraints gbc = new GridBagConstraints();

                RoundedButton saveButton = new RoundedButton("Save");
                saveButton.setPreferredSize(new Dimension(100, 30));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(saveButton);

                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(buttonPanel, gbc);

                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.CENTER;

                panel.add(new JLabel("   Group:"), gbc);
                gbc.gridy++;
                panel.add(groupComboBox, gbc);
                gbc.gridy++;
                panel.add(new JLabel("   Name:"), gbc);
                gbc.gridy++;
                panel.add(nameField, gbc);
                gbc.gridy++;
                panel.add(new JLabel("   Description:"), gbc);
                gbc.gridy++;
                panel.add(descriptionField, gbc);
                gbc.gridy++;
                panel.add(new JLabel("   Manufacturer:"), gbc);
                gbc.gridy++;
                panel.add(manufacturerField, gbc);
                gbc.gridy++;
                panel.add(new JLabel("   Quantity:"), gbc);
                gbc.gridy++;
                panel.add(quantityField, gbc);
                gbc.gridy++;
                panel.add(new JLabel("   Price:"), gbc);
                gbc.gridy++;
                panel.add(priceField, gbc);
                gbc.gridy++;
                panel.add(new JLabel(" "), gbc);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedGroup = (String) groupComboBox.getSelectedItem();
                        String name = nameField.getText().toLowerCase();
                        String description = descriptionField.getText();
                        String manufacturer = manufacturerField.getText();
                        int quantity = Integer.parseInt(quantityField.getText());
                        double price = Double.parseDouble(priceField.getText());

                        try {
                            quantity = Integer.parseInt(quantityField.getText());
                            if (quantity < 0) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Quantity cannot be negative. Please enter a different value.");
                            return; // Exit the method
                        }

                        try {
                            price = Double.parseDouble(priceField.getText());
                            if (price < 0) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Price cannot be negative. Please enter a different value.");
                            return; // Exit the method
                        }
                        // Create a HashSet to store item names
                        HashSet<String> itemNames = new HashSet<>();
                        for (Product item : Product.getProducts()) {
                            itemNames.add(item.getName().toLowerCase()); // Convert each item name to lower case
                        }
                        // Check if the item name already exists (case-insensitive)
                        if (!itemNames.contains(name)) {
                            ProductGroup group = ProductGroup.findGroupByName(selectedGroup);
                            // Create a new product and add it to the products list
                            Product newProduct = new Product(name, description, manufacturer, quantity, price, group);
                            Product.getProducts().add(newProduct);
                            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                            model.addRow(new Object[]{newProduct.getGroup(), newProduct.getName(), newProduct.getDescription(), newProduct.getManufacturer(), newProduct.getQuantity(), newProduct.getPrice()});

                            // Close the dialog
                            Window win = SwingUtilities.getWindowAncestor(saveButton);
                            if (win != null) {
                                win.dispose();
                            }
                            Statistics.updateTotalCostLabel();
                        } else {
                            JOptionPane.showMessageDialog(null, "Item name already exists. Please enter a different name.");
                        }
                    }
                });

                panel.add(saveButton);

                JDialog dialog = new JDialog();
                dialog.setModal(true);
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog("Enter product name to search");
                DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                model.setRowCount(0); // Clear the table
                HashSet<String> productNames = new HashSet<>();

                for (Product product : Product.products) {
                    if (product.getName().contains(search) && !productNames.contains(product.getName())) {
                        productNames.add(product.getName());
                        model.addRow(new Object[]{product.getGroup(), product.getName(), product.getDescription(), product.getManufacturer(), product.getQuantity(), product.getPrice()});
                    }
                }
                if (model.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Product not found");
                }
            }
        });

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new dialog
                JDialog dialog = new JDialog();
                dialog.setModal(true);

                // Create a new panel for the dialog
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 1));

                // Create a new combo box for the items
                JComboBox<Product> itemComboBox = new JComboBox<>();
                HashSet<String> productNames = new HashSet<>();
                for (Product product : Product.getProducts()) {
                    if (!productNames.contains(product.getName())) {
                        productNames.add(product.getName());
                        itemComboBox.addItem(product);
                    }
                }
                panel.add(itemComboBox);

                // Create a new delete button
                RoundedButton deleteButton = new RoundedButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Product selectedItem = (Product) itemComboBox.getSelectedItem();
                        if (selectedItem != null) {
                            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete item \"" + selectedItem + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {

                                //delete  item twice idk why but it works !
                                Product.deleteProduct(selectedItem);
                                Product.deleteProduct(selectedItem);
                                Product.writeItemsToFile();

                                // delete item logic
                                for (ProductGroup group : existingGroups) {
                                    for (Product product : group.getProducts()) {
                                        if (product.getName().equals(selectedItem)) {
                                            group.getProducts().remove(product);
                                            Product.getProducts().remove(product); // Remove the product from the static products list
                                            break;
                                        }
                                    }
                                }
                                JOptionPane.showMessageDialog(null, "Item \"" + selectedItem + "\" deleted successfully.");
                                updateProductTable(); // Update the product table
                                //      updateGroupTabAfterDelete(); // Update the group tab
                            }
                            Statistics.updateTotalCostLabel();
                        } else {
                            JOptionPane.showMessageDialog(null, "No item selected.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        dialog.dispose(); // Close the dialog
                    }
                });
                panel.add(deleteButton);

                // Add the panel to the dialog and display the dialog
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        changeQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoundedComboBox productComboBox = new RoundedComboBox();
                for (Product product : Product.getProducts()) {
                    productComboBox.addItem(product);
                }
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 2));

                JLabel nameLabel = new JLabel("Select product:");
                nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(nameLabel);
                panel.add(productComboBox);

                JSpinner quantityField = new JSpinner();
                quantityField.setEditor(new JSpinner.DefaultEditor(quantityField));
                quantityField.setValue(0);
                // quantityField.setValue(Product.findItemByName(productComboBox.getSelectedItem().toString()).getQuantity());
                quantityField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(new JLabel("Quantity:"));
                panel.add(quantityField);

                RoundedButton saveButton = new RoundedButton("Save");
                saveButton.setPreferredSize(new Dimension(100, 30));
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(saveButton);
                panel.add(buttonPanel);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Product selectedProduct = (Product) productComboBox.getSelectedItem();
                        int newQuantity;
                        try {
                            newQuantity = Integer.parseInt(quantityField.getValue().toString());
                        } catch (NumberFormatException ex) {
                            newQuantity = 0; // Default to 0 if input is not a valid number
                        }

                        int currentQuantity = selectedProduct.getQuantity();
                        int updatedQuantity = currentQuantity + newQuantity;

                        // Check if the updated quantity is negative
                        if (updatedQuantity < 0) {
                            JOptionPane.showMessageDialog(null, "Quantity cannot be negative. Please enter a different value.");
                        } else {
                            selectedProduct.setQuantity(updatedQuantity);
                            updateProductTable();
                            Product.writeItemsToFile();
                            ProductGroup.writeGroupsToFile();

                            Window win = SwingUtilities.getWindowAncestor(saveButton);
                            if (win != null) {
                                win.dispose();
                            }
                            Statistics.updateTotalCostLabel();
                        }
                    }
                });

                JDialog dialog = new JDialog();
                dialog.setModal(true);
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<Product> uniqueProducts = new HashSet<>(Product.getProducts());
                JComboBox<Product> productComboBox = new JComboBox<>(uniqueProducts.toArray(new Product[0]));

                HashSet<ProductGroup> uniqueGroups = new HashSet<>(ProductGroup.getExistingGroups());
                JComboBox<ProductGroup> groupComboBox = new JComboBox<>(uniqueGroups.toArray(new ProductGroup[0]));
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 2));

                JLabel nameLabel = new JLabel("Select product:");
                nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(nameLabel);
                panel.add(productComboBox);

                JLabel groupLabel = new JLabel("Select group:");
                groupLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(groupLabel);
                panel.add(groupComboBox);

                RoundedTextField newNameField = new RoundedTextField(15);
                newNameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                panel.add(new JLabel("Enter new product name:"));
                panel.add(newNameField);

                RoundedTextField descriptionField = new RoundedTextField(15);
                descriptionField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(new JLabel("Enter product description:"));
                panel.add(descriptionField);

                RoundedTextField manufacturerField = new RoundedTextField(15);
                manufacturerField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(new JLabel("Enter manufacturer:"));
                panel.add(manufacturerField);

                RoundedTextField quantityField = new RoundedTextField(15);
                quantityField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(new JLabel("Enter quantity:"));
                panel.add(quantityField);

                RoundedTextField priceField = new RoundedTextField(15);
                priceField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(new JLabel("Enter price:"));
                panel.add(priceField);

                RoundedButton saveButton = new RoundedButton("Save");
                saveButton.setPreferredSize(new Dimension(100, 30));
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(saveButton);
                panel.add(buttonPanel);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Product selectedProduct = (Product) productComboBox.getSelectedItem();
                        ProductGroup selectedGroup = (ProductGroup) groupComboBox.getSelectedItem();
                        String newName = newNameField.getText().trim().toLowerCase();
                        String newDescription = descriptionField.getText().trim();
                        String newManufacturer = manufacturerField.getText().trim();
                        int newQuantity;
                        double newPrice;

                        try {
                            newQuantity = Integer.parseInt(quantityField.getText().trim());
                            if (newQuantity < 1) {
                                JOptionPane.showMessageDialog(null, "Quantity cannot be negative");
                                return; // Exit the method
                            }
                        } catch (NumberFormatException ex) {
                            newQuantity = selectedProduct.getQuantity(); // Збереження попереднього значення
                        }
                        try {
                            newPrice = Double.parseDouble(priceField.getText().trim());
                            if (newPrice < 0.1) {
                                JOptionPane.showMessageDialog(null, "Price cannot be negative");
                                return; // Exit the method
                            }
                        } catch (NumberFormatException ex) {
                            newPrice = selectedProduct.getPrice(); // Збереження попереднього значення
                        }

                        // Create a HashSet to store item names
                        HashSet<String> itemNames = new HashSet<>();
                        for (Product item : Product.getProducts()) {
                            if (!item.equals(selectedProduct)) { // Exclude the current item from the check
                                itemNames.add(item.getName().toLowerCase()); // Convert each item name to lower case
                            }
                        }

                        // Check if the item name already exists (case-insensitive)
                        if (!itemNames.contains(newName)) {
                            // Remove product from previous group
                            selectedProduct.getGroup().deleteProduct(selectedProduct);

                            // Update product properties considering filled and previous values
                            selectedProduct.setName(newName.isEmpty() ? selectedProduct.getName() : newName);
                            selectedProduct.setGroup(selectedGroup);
                            selectedProduct.setDescription(newDescription.isEmpty() ? selectedProduct.getDescription() : newDescription);
                            selectedProduct.setManufacturer(newManufacturer.isEmpty() ? selectedProduct.getManufacturer() : newManufacturer);
                            selectedProduct.setQuantity(newQuantity);
                            selectedProduct.setPrice(newPrice);

                            // Add product to new group
                            selectedGroup.addProduct(selectedProduct);

                            // Update product table
                            updateProductTable();
                            Product.writeItemsToFile();
                            ProductGroup.writeGroupsToFile();

                            // Close the dialog
                            Window win = SwingUtilities.getWindowAncestor(saveButton);
                            if (win != null) {
                                win.dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Item name already exists. Please enter a different name.");
                        }
                    }
                });


                // Створення та налаштування діалогового вікна
                JDialog dialog = new JDialog();
                dialog.setModal(true);
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

    }

        // updates combobox!!
    public static void updateGroupComboBox() {
        System.out.println("updateGroupComboBox called"); // Debugging line
        ArrayList<String> groupNames = new ArrayList<>();
        if (groupComboBox.getItemAt(0) != null) {

            groupComboBox.removeAllItems();
        }
        for (ProductGroup group : ProductGroup.groups) {
            groupNames.add(group.getName());
        }
        groupComboBox.setModel(new DefaultComboBoxModel<>(groupNames.toArray(new String[0])));
    }

    public static void updateProductTableWithProduct(Product newProduct) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.addRow(new Object[]{newProduct.getGroup(), newProduct.getName(), newProduct.getDescription(), newProduct.getManufacturer(), newProduct.getQuantity(), newProduct.getPrice()});
    }

    private static Object[][] getProductData() {
        ArrayList<Product> products = Product.getProducts();
        Object[][] data = new Object[products.size()][7];
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = Boolean.FALSE; // Initialize the checkbox as unchecked
            data[i][1] = product.getGroup();
            data[i][2] = product.getName();
            data[i][3] = product.getDescription();
            data[i][4] = product.getManufacturer();
            data[i][5] = product.getQuantity();
            data[i][6] = product.getPrice();
        }
        return data;
    }
    //update the table
    static void updateProductTable(){
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();

        model.setRowCount(0);

        ArrayList<Product> products = Product.getProducts();
        HashSet<String> productNames = new HashSet<>();

        products.sort(Comparator.comparing(product -> product.getGroup().getName()));

        for (Product product : products) {
            if (!productNames.contains(product.getName())) {
                productNames.add(product.getName());
                model.addRow(new Object[]{product.getGroup(), product.getName(), product.getDescription(), product.getManufacturer(), product.getQuantity(), product.getPrice()});
            }
        }
    }
}