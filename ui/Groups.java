package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import structure.Product;
import structure.ProductGroup;
import uimodels.DescriptionField;
import uimodels.GroupList;
import uimodels.RoundedButton;
import uimodels.RoundedTextField;
import uimodels.Table;

public class Groups extends JPanel {
    private JPanel rightPanel;
    private JLabel infoLabel;
    private DescriptionField groupDescription;
    public static DefaultListModel<ProductGroup> groupListModel; // Model for JList
    private GroupList groupList;
    private ProductGroup selectedGroup;


    public Groups() {

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Groups of items");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        infoLabel = new JLabel();
        rightPanel.add(infoLabel, BorderLayout.NORTH);
        rightPanel.setBackground(Color.getHSBColor(2.0f/100, 0.0f/100, 98.0f/100)  );

        groupDescription = new DescriptionField();

        groupDescription.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        groupDescription.setText("                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ");

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 340));
        leftPanel.setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        rightPanel.setPreferredSize(new Dimension(370, 340));

        groupListModel = new DefaultListModel<>();
        groupList = new GroupList(groupListModel);
        groupList.getGroupList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.getGroupList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProductGroup selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    rightPanel.removeAll(); // Clear the rightPanel

                    JLabel blank = new JLabel();
                    blank.setText("  ");
                    infoLabel.setText("   Group " + selectedGroup.getName());
                    infoLabel.setFont(new Font("Century Gothic", Font.BOLD, 15));
                    groupDescription.setText(selectedGroup.getDescription()); // Update JTextArea with group description
                    JPanel infoPanel = new JPanel();
                    infoPanel.setLayout(new BorderLayout());
                    infoPanel.setBackground(Color.getHSBColor(2.0f/100, 1.0f/100, 98.0f/100));
                    infoPanel.add(blank, BorderLayout.NORTH); // Add blank to the NORTH (top of the panel
                    infoPanel.add(infoLabel, BorderLayout.NORTH); // Add infoLabel to the CENTER

                    //    if(selectedGroup.getProductByIndex(0)!=null){
                    String[] columnNames = {"Name", "Description", "Manufacturer", "Quantity", "Price"};
                    Object[][] data = getProductData(selectedGroup);

                    DefaultTableModel model = new DefaultTableModel(data, columnNames);
                    Table itemslist = new Table(model);
                    itemslist.setFillsViewportHeight(true);
                    JScrollPane scrollPane = new JScrollPane(itemslist);
                    infoPanel.add(scrollPane, BorderLayout.CENTER); // Add scrollPane to the CENTER of infoPanel
                    // }

                    infoPanel.add(infoLabel, BorderLayout.NORTH); // Add infoLabel to the NORTH

                    infoPanel.add(infoLabel, BorderLayout.NORTH); // Add infoLabel to the NORTH

                    rightPanel.add(infoPanel, BorderLayout.CENTER); // Add infoPanel to rightPanel
                    groupDescription.revalidate(); // Revalidate the JTextArea
                    groupDescription.repaint(); // Repaint the JTextArea
                    add(rightPanel,BorderLayout.EAST);
                }
                else{
                    infoLabel.setText("");
                    groupDescription.setText("");
                }

                rightPanel.revalidate(); // Revalidate the rightPanel
                rightPanel.repaint();
            }
        });

        JScrollPane groupListScrollPane = new JScrollPane(groupList);
        groupListScrollPane.setPreferredSize(new Dimension(130, 140));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        listPanel.add(groupListScrollPane);
        leftPanel.setPreferredSize(new Dimension(150, 300));
        leftPanel.add(listPanel, BorderLayout.CENTER);

        // Create a new JPanel for the buttons
        JPanel buttonPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        buttonPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);// Create the buttons
        RoundedButton addGroupButton = new RoundedButton("Add");
        addGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        layout.setConstraints(addGroupButton, gbc);
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoundedTextField nameField = new RoundedTextField(15);
                RoundedTextField descriptionField = new RoundedTextField(15);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 2));

                JLabel label = new JLabel("Enter group name:");
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(label);

                nameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(nameField);

                JLabel label2 = new JLabel("Enter group description:");
                label2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(label2);

                descriptionField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(descriptionField);
                RoundedButton saveButton = new RoundedButton("Save");
                saveButton.setPreferredSize(new Dimension(100, 30));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(saveButton);
                panel.add(buttonPanel);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText().toLowerCase();
                        String description = descriptionField.getText();

                        // Create a HashSet to store group names
                        HashSet<String> groupNames = new HashSet<>();
                        for (ProductGroup group : ProductGroup.groups) {
                            groupNames.add(group.getName().toLowerCase());
                        }
                        // Check if the group name already exists
                        if (!groupNames.contains(name)) {
                            ProductGroup newGroup = new ProductGroup(name, description, true);
                            groupListModel.addElement(newGroup);
                            Window win = SwingUtilities.getWindowAncestor(saveButton);
                            if (win != null) {
                                win.dispose();
                            }
                            Statistics.updateGroupComboBox();
                        } else {
                            JOptionPane.showMessageDialog(null, "Group name already exists. Please enter a different name.");
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
            }});
        RoundedButton deleteGroupButton = new RoundedButton("Delete");
        deleteGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        layout.setConstraints(deleteGroupButton, gbc);
        deleteGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductGroup selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    groupListModel.removeElement(selectedGroup);
                    ProductGroup.groups.remove(selectedGroup);
                    ProductGroup.deleteGroup(selectedGroup);

                    // Remove all products that belong to the selected group
                    ArrayList<Product> products = Product.getProducts();
                    products.removeIf(product -> product.getGroup() != null && product.getGroup().equals(selectedGroup));
                    Items.updateProductTable();
                    // new ProductGroup("", "", true).writeGroupsToFile();
                    // Items.updateGroupComboBox();
                    Statistics.updateGroupComboBox();
                }

            }});
        RoundedButton editGroupButton = new RoundedButton("Edit");
        editGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        layout.setConstraints(editGroupButton, gbc);
        editGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedGroup = groupList.getSelectedValue();
                RoundedTextField nameField = new RoundedTextField(15);
                RoundedTextField descriptionField = new RoundedTextField(15);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 2));

                JLabel label = new JLabel("Enter group name:");
                label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(label);

                nameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(nameField);

                JLabel label2 = new JLabel("Enter group description:");
                label2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(label2);

                descriptionField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.add(descriptionField);
                RoundedButton saveButton = new RoundedButton("Save");
                saveButton.setPreferredSize(new Dimension(100, 30));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(saveButton);
                panel.add(buttonPanel);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText().toLowerCase();
                        String description = descriptionField.getText();
                        // Create a HashSet to store group names
                        HashSet<String> groupNames = new HashSet<>();
                        for (ProductGroup group : ProductGroup.groups) {
                            if (!group.equals(selectedGroup)) { // Exclude the current group from the check
                                groupNames.add(group.getName().toLowerCase()); // Convert each group name to lower case
                            }
                        }
                        // Check if the group name already exists (case-insensitive)
                        if (!groupNames.contains(name)) {
                            selectedGroup.setName(name);
                            selectedGroup.setDescription(description);
                            ProductGroup.writeGroupsToFile();
                            groupListModel.setElementAt(selectedGroup, groupList.getSelectedIndex());
                            infoLabel.setText("   Group " + selectedGroup.getName());
                            groupDescription.setText(selectedGroup.getDescription());
                            Window win = SwingUtilities.getWindowAncestor(saveButton);
                            if (win != null) {
                                win.dispose();
                            }
                            Statistics.updateGroupComboBox();
                        } else {
                            JOptionPane.showMessageDialog(null, "Group name already exists. Please enter a different name.");
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

        RoundedButton searchGroupButton = new RoundedButton("Search it");
        searchGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        layout.setConstraints(searchGroupButton, gbc);
        searchGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog("Enter group name to search:");
                DefaultListModel<ProductGroup> searchListModel = new DefaultListModel<>();
                for (ProductGroup group : ProductGroup.groups) {
                    if (group.getName().contains(search)) {
                        searchListModel.addElement(group);
                    }
                }
                if (!searchListModel.isEmpty()) {
                    groupList.setModel(searchListModel);
                    groupList.getGroupList().setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "group not found !! oopsie");
                }
            }
        });

        RoundedButton viewAllGroupsButton = new RoundedButton("View all");
        viewAllGroupsButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        layout.setConstraints(viewAllGroupsButton, gbc);
        viewAllGroupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupList.setModel(groupListModel);
            }
        });

        gbc.gridx = 0; // set gridx
        gbc.gridy = GridBagConstraints.RELATIVE; // set gridy to the next available row
        gbc.gridwidth = 1; // set gridwidth
        gbc.gridheight = 1; // set gridheight
        gbc.weightx = 1.0; // request any extra horizontal space
        gbc.weighty = 1.0; // request any extra vertical space
        gbc.fill = GridBagConstraints.HORIZONTAL; // set fill
        gbc.insets = new Insets(10, 10, 10, 10); // set padding

        buttonPanel.add(addGroupButton);
        buttonPanel.add(deleteGroupButton);
        buttonPanel.add(editGroupButton);
        buttonPanel.add(searchGroupButton);
        buttonPanel.add(viewAllGroupsButton);
        gbc.gridx = 1; // set gridx
        gbc.gridy = 5; // set gridy
        gbc.gridwidth = 3; // set gridwidth to 3
        gbc.gridheight = 3; // set gridheight to 3
        gbc.weightx = 1; // request any extra horizontal space
        gbc.weighty = 1; // request any extra vertical space
        gbc.fill = GridBagConstraints.BOTH;
        buttonPanel.add(groupDescription, gbc);
        buttonPanel.setBackground(Color.getHSBColor(2.0f/100, 0.0f/100, 98.0f/100));
        add(buttonPanel, BorderLayout.SOUTH);


    }
    private static Object[][] getProductData(ProductGroup selectedGroup) {
        ArrayList<Product> products = Product.getProducts();
        ArrayList<Product> selectedGroupProducts = new ArrayList<>(); // To store products from the selected group
        HashSet<String> productNames = new HashSet<>(); // To store unique product names

        for (Product product : products) {
            if (product.getGroup() != null && product.getGroup().equals(selectedGroup) && !productNames.contains(product.getName())) {
                productNames.add(product.getName());
                selectedGroupProducts.add(product);
            }
        }

        // If the selected group doesn't have any items, return null
        if (selectedGroupProducts.isEmpty()) {
            return null;
        }

        Object[][] data = new Object[selectedGroupProducts.size()][5];

        for (int i = 0; i < selectedGroupProducts.size(); i++) {
            Product product = selectedGroupProducts.get(i);
            data[i][0] = product.getName();
            data[i][1] = product.getDescription();
            data[i][2] = product.getManufacturer();
            data[i][3] = product.getQuantity();
            data[i][4] = product.getPrice();
        }

        return data;
    }
}