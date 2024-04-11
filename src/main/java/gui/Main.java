package gui;

import service.RegisterService;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main {

    private JFrame frame;
    private JPanel panel1;
    private JLabel nameLabel;
    private JComboBox<String> nameCBox;
    private JButton selectButton;

    public Main() {
        frame = new JFrame("Main");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Fiado");

        nameCBox.addItem("Kaiwen");

        selectButton.addActionListener(selectButtonAction(frame));
    }

    private ActionListener selectButtonAction(JFrame mainFrame) {
        return e -> {
            mainFrame.dispose();
            Register register = new Register(Objects.requireNonNull(nameCBox.getSelectedItem()).toString());
            RegisterService registerService = new RegisterService(register);
            registerService.createWindow();
        };
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
