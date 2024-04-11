import javax.swing.*;
import java.awt.*;

public class Items extends JPanel {
    public Items() {

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Groups of goods");
        titleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Вирівнювання тексту по центру

        add(titleLabel, BorderLayout.NORTH);

        JButton searchButton = new JButton("Search");
        JButton addGroupButton = new JButton("Add Item");
        JButton deleteGroupButton = new JButton("Delete Item");

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Font buttonFont = new Font("Century Gothic", Font.PLAIN, 16);
        searchButton.setFont(buttonFont);
        addGroupButton.setFont(buttonFont);
        deleteGroupButton.setFont(buttonFont);

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(addGroupButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteGroupButton);


        add(buttonPanel, BorderLayout.SOUTH);
    }
}
