package ui;

import structure.ProductGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import structure.Product;
import uimodels.RoundedButton;
import uimodels.RoundedComboBox;
import uimodels.Table;


public class Items extends JPanel {
    private JTextArea productListTextArea;
    private JPanel productPanel;
    private static JComboBox<String> groupComboBox;
    private ArrayList<ProductGroup> existingGroups;
private static Table productTable;
    public Items(ArrayList<ProductGroup> existingGroups) {
        setLayout(new BorderLayout());
        this.existingGroups = existingGroups;
        JLabel titleLabel = new JLabel("Items");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel  buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        RoundedButton searchButton = new RoundedButton("Search");
        RoundedButton addItemButton = new RoundedButton("Add Item");
        RoundedButton deleteItemButton = new RoundedButton("Delete Item");

        buttonPanel.add(searchButton);
        buttonPanel.add(addItemButton);
        buttonPanel.add(deleteItemButton);

        groupComboBox = new RoundedComboBox();
        groupComboBox.setPreferredSize(new Dimension(220, 25));
        buttonPanel.add(groupComboBox); //комбобокс на панелі кнопок пох пон

        add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"Group", "Name", "Description", "Manufacturer", "Quantity", "Price"};
        Object[][] data = getProductData();

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        productTable = new Table(model);
        productTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane productScrollPane = new JScrollPane(productPanel);
        add(productScrollPane, BorderLayout.EAST);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = (String) groupComboBox.getSelectedItem();
                if (selectedGroup == null) {
                    JOptionPane.showMessageDialog(null, "Please select a group first.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

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
                ProductGroup group = ProductGroup.findGroupByName(selectedGroup);
                // Create a new product and add it to the products list
                Product newProduct = new Product(name, description, manufacturer, quantity, price, group);
                newProduct.setGroup(ProductGroup.findGroupByName(selectedGroup));
                Product.getProducts().add(newProduct);
                newProduct.setGroup(ProductGroup.findGroupByName(selectedGroup));
                DefaultTableModel model = (DefaultTableModel) productTable.getModel();
                model.addRow(new Object[]{newProduct.getGroup(), newProduct.getName(), newProduct.getDescription(), newProduct.getManufacturer(), newProduct.getQuantity(), newProduct.getPrice()});}
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

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGroup = (String) groupComboBox.getSelectedItem();
                if (selectedGroup != null) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete group \"" + selectedGroup + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // delete group logic
                        for (ProductGroup group : existingGroups) {
                            if (group.getName().equals(selectedGroup)) {
                                ProductGroup.deleteGroup(group);
                                break;
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Group \"" + selectedGroup + "\" deleted successfully.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No group selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // updates combobox!!
    public static void updateGroupComboBox() {
        System.out.println("updateGroupComboBox called"); // Debugging line
        ArrayList<String> groupNames = new ArrayList<>();
        if(groupComboBox.getItemAt(0) != null) {

        groupComboBox.removeAllItems();}
        for (ProductGroup group : ProductGroup.groups) {
            groupNames.add(group.getName());
        }
        groupComboBox.setModel(new DefaultComboBoxModel<>(groupNames.toArray(new String[0])));
    }

    public static void updateProductTable(Product newProduct) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.addRow(new Object[]{newProduct.getGroup(), newProduct.getName(), newProduct.getDescription(), newProduct.getManufacturer(), newProduct.getQuantity(), newProduct.getPrice()});
    }
    private static Object[][] getProductData() {
        ArrayList<Product> products = Product.getProducts();
        Object[][] data = new Object[products.size()][6];
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getGroup();
            data[i][1] = product.getName();
            data[i][2] = product.getDescription();
            data[i][3] = product.getManufacturer();
            data[i][4] = product.getQuantity();
            data[i][5] = product.getPrice();
        }
        return data;
    }


}
