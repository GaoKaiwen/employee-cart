package gui;

import utils.JMoneyField;

import javax.swing.*;

public class Register {

    private JPanel panel2;
    private JLabel productLabel;
    private JTextField productField;
    private JLabel priceLabel;
    private JFormattedTextField priceField;
    private JSpinner productQuantitySpinner;
    private JLabel quantityLabel;
    private JButton consultButton;
    private JButton registerButton;
    private String employee;

    public Register(String employee) {
        this.employee = employee;
    }

    public void cleanAllFields() {
        productField.setText("");
        priceField.setText("");
        productQuantitySpinner.setValue(1);
    }

    private void createUIComponents() {
        SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, 100, 1);
        productQuantitySpinner = new JSpinner(sm);

        priceField = new JMoneyField();
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public JLabel getProductLabel() {
        return productLabel;
    }

    public JTextField getProductField() {
        return productField;
    }

    public JLabel getPriceLabel() {
        return priceLabel;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JSpinner getProductQuantitySpinner() {
        return productQuantitySpinner;
    }

    public JLabel getQuantityLabel() {
        return quantityLabel;
    }

    public JButton getConsultButton() {
        return consultButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public String getEmployee() {
        return employee;
    }
}
