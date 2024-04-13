package uimodels;

import structure.ProductGroup;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.ArrayList;

public class GroupList extends JPanel {
    private DefaultListModel<ProductGroup> groupListModel;
    private JList<ProductGroup> groupList;
    private ArrayList<ProductGroup> groups;

    public GroupList(DefaultListModel<ProductGroup> model) {
        setLayout(new BorderLayout());

        groups = new ArrayList<>();
        groupListModel = model;
        groupList = new JList<>(groupListModel);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.setFont(new Font("Century Gothic", Font.PLAIN, 13));
       // groupList.setBackground(Color.getHSBColor(2.0f/100, 0.0f/100, 98.0f/100));        groupList.setForeground(Color.WHITE); // Add this line
//setting background breaks everything??? SF?S FoS'lksLKF:sLK:
        groupList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(new LineBorder(Color.getHSBColor(10, 35,230), 1, true));
                label.setBackground(Color.white);
                label.setOpaque(true);
                label.setHorizontalAlignment(JLabel.CENTER); // Center the label horizontally
                label.setVerticalAlignment(JLabel.CENTER); // Center the label vertically
                return label;
            }
        });


        JScrollPane groupListScrollPane = new JScrollPane(groupList);
        groupListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        groupListScrollPane.setBorder(new LineBorder(Color.getHSBColor(10, 35,230), 1, true));
        groupListScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.getHSBColor(101, 120,180); // Change to your preferred color
                this.trackColor = Color.getHSBColor(10, 35,270); // Change to your preferred color
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        groupListScrollPane.setPreferredSize(new Dimension(130, 140));
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.add(groupListScrollPane);
        add(groupListScrollPane, BorderLayout.CENTER);
    }

    public void addGroup(ProductGroup group) {
        groups.add(group);
        groupListModel.addElement(group);
    }

    public void deleteGroup(ProductGroup group) {
        groups.remove(group);
        groupListModel.removeElement(group);
    }

    public void editGroup(ProductGroup oldGroup, ProductGroup newGroup) {
        int index = groups.indexOf(oldGroup);
        if (index != -1) {
            groups.set(index, newGroup);
            groupListModel.setElementAt(newGroup, index);
        }
    }

    public ProductGroup getSelectedGroup() {
        return groupList.getSelectedValue();
    }

    public ProductGroup getSelectedValue() {
        return groupList.getSelectedValue();
    }

    public JList getGroupList() {
        return groupList;
    }
}