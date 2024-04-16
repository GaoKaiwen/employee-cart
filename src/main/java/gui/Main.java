package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {

    private JFrame frame;
    private JPanel panel1;
    private JLabel nameLabel;
    private JComboBox<String> nameCBox;
    private JButton selectButton;
    private JButton signUpButton;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JComboBox<String> getNameCBox() {
        return nameCBox;
    }

    public JButton getSelectButton() {
        return selectButton;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }
}
