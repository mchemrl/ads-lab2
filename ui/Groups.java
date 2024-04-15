package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import structure.ProductGroup;
import uimodels.DescriptionField;
import uimodels.GroupList;
import uimodels.RoundedButton;
import uimodels.RoundedTextField;
import structure.GroupObserver;
public class Groups extends JPanel {
    private JPanel rightPanel;
    private JLabel infoLabel;
    private DescriptionField groupDescription;
  //  private JTextArea groupDescription; // JTextArea for group description
    ProductGroup group;
    private DefaultListModel<ProductGroup> groupListModel; // Model for JList
   // private JList<structure.ProductGroup> groupList; // JList of structure.ProductGroup
private GroupList groupList;
    private ProductGroup selectedGroup;
    private ArrayList<GroupObserver> observers = new ArrayList<>();


    public Groups() {




        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Groups of items");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // Set layout to BorderLayout
        infoLabel = new JLabel();
        rightPanel.add(infoLabel, BorderLayout.NORTH); // Add infoLabel to the top

        groupDescription = new DescriptionField(); // Initialize JTextArea

        groupDescription.setFont(new Font("Century Gothic", Font.PLAIN, 13));
      //  groupDescription.setPreferredSize(new Dimension(150, 100));
        groupDescription.setText("                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ");





        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 340));
        leftPanel.setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);

       // groups = new ArrayList<>(); // Initialize ArrayList
        groupListModel = new DefaultListModel<>(); // Initialize DefaultListModel
        groupList = new GroupList(groupListModel); // Initialize JList with DefaultListModel
        groupList.getGroupList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.getGroupList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ProductGroup selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    infoLabel.setText("bla bla " + selectedGroup.getName());
                    groupDescription.setText(selectedGroup.getDescription()); // Update JTextArea with group description
                    groupDescription.revalidate(); // Revalidate the JTextArea
                    groupDescription.repaint(); // Repaint the JTextArea
                }
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
        RoundedButton addGroupButton = new RoundedButton("Add Group");
        addGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        layout.setConstraints(addGroupButton, gbc);
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoundedTextField nameField = new RoundedTextField(15);
                RoundedTextField descriptionField = new RoundedTextField(15);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 1));
                panel.add(new JLabel("Enter group name:"));
                panel.add(nameField);
                panel.add(new JLabel("Enter group description:"));
                panel.add(descriptionField);

                RoundedButton saveButton = new RoundedButton("Save");
                saveButton.setPreferredSize(new Dimension(10, 30));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(saveButton);

                panel.add(buttonPanel);
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String description = descriptionField.getText();
                        ProductGroup newGroup = new ProductGroup(name, description);
                        groupListModel.addElement(newGroup);
                        Items.updateGroupComboBox();
                        Window win = SwingUtilities.getWindowAncestor(saveButton);
                        if (win != null) {
                            win.dispose();
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
        RoundedButton deleteGroupButton = new RoundedButton("Delete Group");
        deleteGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        layout.setConstraints(deleteGroupButton, gbc);
        deleteGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected group
                ProductGroup selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    // Remove the selected group from the JList
                    groupListModel.removeElement(selectedGroup);
                    // Remove the selected group from the ArrayList
                    ProductGroup.groups.remove(selectedGroup);
                    // Remove the selected group from the file
                    ProductGroup.deleteGroup(selectedGroup);
                    // Rewrite the file
                    new ProductGroup("", "").writeGroupNamesToFile();
                }
            }
        });
        RoundedButton editGroupButton = new RoundedButton("Edit Group");
        editGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        layout.setConstraints(editGroupButton, gbc);
        editGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductGroup selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    RoundedTextField nameField = new RoundedTextField(15);
                    nameField.setText(selectedGroup.getName());
                    RoundedTextField descriptionField = new RoundedTextField(15);
                    descriptionField.setText(selectedGroup.getDescription());

                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(0, 1));
                    panel.add(new JLabel("Enter new group name:"));
                    panel.add(nameField);
                    panel.add(new JLabel("Enter new group description:"));
                    panel.add(descriptionField);

                    JButton saveButton = new JButton("Save");
                    saveButton.setPreferredSize(new Dimension(10, 30));

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.add(saveButton);

                    panel.add(buttonPanel);
                   // panel.add(saveButton);

                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name = nameField.getText();
                            String description = descriptionField.getText();
                            ProductGroup newGroup = new ProductGroup(name, description);
                            ProductGroup.groups.add(newGroup);
                            groupListModel.addElement(newGroup);
                           // notifyObservers(newGroup); // Notify observers
                            Window win = SwingUtilities.getWindowAncestor(saveButton);
                            if (win != null) {
                                win.dispose();
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
            }
        });
        RoundedButton searchGroupButton = new RoundedButton("Search Group");
        searchGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        layout.setConstraints(searchGroupButton, gbc);
        searchGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog("Enter group name to search");
                for (ProductGroup group : ProductGroup.groups) {
                    if (group.getName().equals(search)) {
                        groupList.getGroupList().setSelectedValue(group, true);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Group not found");
            }
        });

        gbc.gridx = 1; // set gridx
        gbc.gridy = 1; // set gridy
        gbc.gridwidth = 2; // set gridwidth
        gbc.gridheight = 5; // set gridheight
        gbc.fill = GridBagConstraints.HORIZONTAL; // set fill

        buttonPanel.add(addGroupButton);
        buttonPanel.add(deleteGroupButton);
        buttonPanel.add(editGroupButton);
        buttonPanel.add(searchGroupButton);
        gbc.gridx = 1; // set gridx
        gbc.gridy = 5; // set gridy
        gbc.gridwidth = 2; // set gridwidth
        gbc.gridheight = 2; // set gridheight
        gbc.weightx = 1; // request any extra horizontal space
        gbc.weighty = 1; // request any extra vertical space
        gbc.fill = GridBagConstraints.BOTH;
         buttonPanel.add(groupDescription, gbc);
         buttonPanel.setBackground(Color.getHSBColor(2.0f/100, 0.0f/100, 98.0f/100));
        add(buttonPanel, BorderLayout.SOUTH);



    }
    public void addObserver(GroupObserver observer) {
        observers.add(observer);
    }

}