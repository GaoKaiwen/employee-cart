package gui;

import utils.JMoneyField;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Register {

    private JPanel panel2;
    private JLabel productLabel;
    private JTextField productField;
    private JLabel priceLabel;
    private JFormattedTextField priceField;
    private JSpinner productQuantitySpinner;
    private JLabel quantityLabel;
    private JButton registerButton;
    private JScrollPane scrollPane;
    private JTable productsTable;
    private JButton cancelButton;
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

    public JButton getRegisterButton() {
        return registerButton;
    }

    public String getEmployee() {
        return employee;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
