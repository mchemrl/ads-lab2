
package uimodels;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class DescriptionField extends JPanel {
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public DescriptionField() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Century Gothic", Font.PLAIN, 13));
        textArea.setRows(0);
      //  textArea.setBorder(new LineBorder(Color.getHSBColor(10, 35,230), 1, true));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(Color.getHSBColor(10, 35,230), 1, true));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(150, 100));

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setText(String text) {
        textArea.setText(text);
    }



}