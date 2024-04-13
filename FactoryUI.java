import javax.swing.*;
import java.awt.*;
public class FactoryUI extends JFrame {
    public FactoryUI() {
        setSize(520, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Century Gothic", Font.PLAIN, 16));

        Groups tab1 = new Groups();
        Items tab2 = new Items();
        Statistics tab3 = new Statistics();

        tabbedPane.addTab("Groups of items", tab1);
        tabbedPane.addTab("Items", tab2);
        tabbedPane.addTab("Statistics", tab3);

        tabbedPane.setBackgroundAt(1, Color.LIGHT_GRAY); // Set the background color of the first tab to red
        tabbedPane.setBackgroundAt(1, Color.LIGHT_GRAY); // Set the background color of the second tab to green
        tabbedPane.setBackgroundAt(2, Color.LIGHT_GRAY); // Set the background color of the third tab to blue

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new FactoryUI();
    }
}
