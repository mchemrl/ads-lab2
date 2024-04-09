import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Groups extends JPanel {
    private JPanel rightPanel;
    private JLabel infoLabel;
    private JTextArea groupDescription; // JTextArea for group description
    private ArrayList<ProductGroup> groups; // ArrayList of ProductGroup
    private DefaultListModel<ProductGroup> groupListModel; // Model for JList
    private JList<ProductGroup> groupList; // JList of ProductGroup

    public Groups() {
        setLayout(new BorderLayout());

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // Set layout to BorderLayout
        infoLabel = new JLabel();
        rightPanel.add(infoLabel, BorderLayout.NORTH); // Add infoLabel to the top

        groupDescription = new JTextArea(); // Initialize JTextArea
        groupDescription.setEditable(false); // Make it non-editable
        rightPanel.add(groupDescription, BorderLayout.CENTER); // Add it to the center

        add(rightPanel, BorderLayout.CENTER);

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
                infoLabel.setText("bla bla " + selectedGroup.getName());
                groupDescription.setText(selectedGroup.getDescription()); // Update JTextArea with group description
            }
        });

        JScrollPane groupListScrollPane = new JScrollPane(groupList);
        groupListScrollPane.setPreferredSize(new Dimension(130, 340));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        listPanel.add(groupListScrollPane);
        leftPanel.setPreferredSize(new Dimension(150, 300));
        leftPanel.add(listPanel, BorderLayout.CENTER);

        // Create a new JPanel for the buttons
        JPanel buttonPanel = new JPanel();

        // Create the buttons
        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 16));
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
        JButton deleteGroupButton = new JButton("Delete Group");
        deleteGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        JButton editGroupButton = new JButton("Edit Group");
        editGroupButton.setFont(new Font("Century Gothic", Font.PLAIN, 16));

        // Add the buttons to the button panel
        buttonPanel.add(addGroupButton);
        buttonPanel.add(deleteGroupButton);
        buttonPanel.add(editGroupButton);

        // Add the button panel to the bottom of the Groups class
        add(buttonPanel, BorderLayout.SOUTH);
    }
}