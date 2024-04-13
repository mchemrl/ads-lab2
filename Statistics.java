import javax.swing.*;
import java.awt.*;

public class Statistics extends JPanel {
    public Statistics() {
        setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("Statistics");
        titleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Вирівнювання тексту по центру
        add(titleLabel, BorderLayout.NORTH);

        JPanel statisticsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label "Price"
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        statisticsPanel.add(priceLabel, gbc);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(calculateButton, gbc);


        JLabel totalCostLabel = new JLabel("Total cost of goods:");
        totalCostLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        statisticsPanel.add(totalCostLabel, gbc);
        double totalPrice = 0;
        JLabel totalPriceLabel = new JLabel("$" + totalPrice);
        totalPriceLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(totalPriceLabel, gbc);

        JLabel selectGroupLabel = new JLabel("Select group:");
        selectGroupLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        statisticsPanel.add(selectGroupLabel, gbc);

        JComboBox<String> groupComboBox = new JComboBox<>();
        groupComboBox.addItem("");
        groupComboBox.addItem("Group 1");
        groupComboBox.addItem("Group 2");
        groupComboBox.addItem("Group 3");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(groupComboBox, gbc);

        JLabel selectItemLabel = new JLabel("Select item:");
        selectItemLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        statisticsPanel.add(selectItemLabel, gbc);

        JComboBox<String> itemComboBox = new JComboBox<>();
        itemComboBox.addItem("");
        itemComboBox.addItem("Item 1");
        itemComboBox.addItem("Item 2");
        itemComboBox.addItem("Item 3");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        statisticsPanel.add(itemComboBox, gbc);


        add(statisticsPanel, BorderLayout.CENTER);
    }
}
