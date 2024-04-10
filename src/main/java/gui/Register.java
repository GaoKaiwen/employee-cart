package gui;

import javax.swing.*;

public class Register {

    private JPanel panel2;
    private JLabel productLabel;
    private JTextField productField;
    private JLabel priceLabel;
    private JTextField priceField;
    private JSpinner productQuantitySpinner;
    private JLabel quantityLabel;
    private JButton consultButton;
    private JButton registerButton;
    private String employee;

    public Register(String employee) {
        this.employee = employee;

        JFrame frame = new JFrame("Register");
        frame.setContentPane(panel2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Fiado - " + employee);

    }


    private void createUIComponents() {
        SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, 100, 1);
        productQuantitySpinner = new JSpinner(sm);
    }
}
