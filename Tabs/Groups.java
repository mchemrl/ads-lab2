package Tabs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Groups extends JPanel {
    private JPanel rightPanel;
    private JLabel infoLabel;

    public Groups() {
        setLayout(new BorderLayout());

        rightPanel = new JPanel();
        infoLabel = new JLabel();
        rightPanel.add(infoLabel);
        add(rightPanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 340));
        leftPanel.setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);

        String[] groups = {"Group 1", "Group 2", "Group 3"};
        JList<String> groupList = new JList<>(groups);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    String selectedGroup = groupList.getSelectedValue();
                    infoLabel.setText("bla bla " + selectedGroup);

            }
        });

        JScrollPane groupListScrollPane = new JScrollPane(groupList);
        groupListScrollPane.setPreferredSize(new Dimension(130, 340));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        listPanel.add(groupListScrollPane);
        leftPanel.setPreferredSize(new Dimension(150, 300));
        leftPanel.add(listPanel, BorderLayout.CENTER);
    }
}