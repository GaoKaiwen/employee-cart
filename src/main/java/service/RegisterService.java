package service;

import gui.Register;
import model.ProductModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

import static utils.BigDecimalUtils.bigDecimalFromCurrencyString;

public class RegisterService {

    private Register register;
    private FileService fileService;

    public RegisterService(Register register) {
        this.register = register;
        fileService = new FileService();
    }

    public void createWindow() {
        JFrame frame = new JFrame("Register");
        setDefaultFrame(frame);

        addListeners();
    }

    private void setDefaultFrame(JFrame frame) {
        frame.setContentPane(register.getPanel2());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Fiado - " + register.getEmployee());

        register.getProductField().setHorizontalAlignment(JTextField.RIGHT);
    }

    private void addListeners() {
        register.getRegisterButton().addActionListener(registerButtonListener());
    }

    private ActionListener registerButtonListener() {
        return e -> {
            ProductModel product = new ProductModel();
            setProductInRegister(product);
            try {
                fileService.saveProduct(product, register.getEmployee());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            register.cleanAllFields();
            JOptionPane.showMessageDialog(null, product.getPrettyPrice());
        };
    }

    private void setProductInRegister(ProductModel product) {
        product.setDescription(register.getProductField().getText());
        product.setPrice(bigDecimalFromCurrencyString(register.getPriceField().getText()));
        product.setQuantity(((int) register.getProductQuantitySpinner().getValue()));
        product.setDate(LocalDate.now());
    }

}
