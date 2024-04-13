import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import uimodels.DescriptionField;
import uimodels.RoundedButton;

public class Groups extends JPanel {
    private JPanel rightPanel;
    private JLabel infoLabel;

    private DescriptionField groupDescription;
  //  private JTextArea groupDescription; // JTextArea for group description
    private ArrayList<ProductGroup> groups; // ArrayList of ProductGroup
    ProductGroup group;
    private DefaultListModel<ProductGroup> groupListModel; // Model for JList
    private JList<ProductGroup> groupList; // JList of ProductGroup

    private ProductGroup selectedGroup;
    public Groups() {
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

        groups = new ArrayList<>(); // Initialize ArrayList
        groupListModel = new DefaultListModel<>(); // Initialize DefaultListModel
        groupList = new JList<>(groupListModel); // Initialize JList with DefaultListModel
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.addMouseListener(new MouseAdapter() {
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
                String name = JOptionPane.showInputDialog("Enter group name");
                String description = JOptionPane.showInputDialog("Enter group description");
                ProductGroup newGroup = new ProductGroup(name, description);
                groups.add(newGroup);
                groupListModel.addElement(newGroup);
            }
        });
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
                    groups.remove(selectedGroup);
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
                // Get the selected group
                ProductGroup selectedGroup = groupList.getSelectedValue();
                if (selectedGroup != null) {
                    // Get the new name and description
                    String name = JOptionPane.showInputDialog("Enter new group name", selectedGroup.getName());
                    String description = JOptionPane.showInputDialog("Enter new group description", selectedGroup.getDescription());
                    // Edit the group
                    ProductGroup.editGroup(selectedGroup, name, description);
                    // Update the JList
                    groupListModel.setElementAt(selectedGroup, groupList.getSelectedIndex());
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
                for (ProductGroup group : groups) {
                    if (group.getName().equals(search)) {
                        groupList.setSelectedValue(group, true);
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
        add(buttonPanel, BorderLayout.SOUTH);
    }
}