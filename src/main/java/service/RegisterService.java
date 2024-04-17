package service;

import exception.CsvParserException;
import exception.CsvRepositoryException;
import gui.Register;
import model.Purchase;
import repository.csv.PurchaseCsvRepository;
import utils.BigDecimalUtils;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import static utils.BigDecimalUtils.bigDecimalFromCurrencyString;

public class RegisterService {

    private final Register register;
    private final PurchaseCsvRepository purchaseCsvRepository;
    private final DefaultTableModel model;

    public RegisterService(Register register) {
        this.register = register;
        this.purchaseCsvRepository = new PurchaseCsvRepository();
        this.model = new DefaultTableModel();
    }

    public void createWindow() {
        JFrame frame = new JFrame("Register");
        setDefaultFrame(frame);
        register.getProductField().setHorizontalAlignment(JTextField.RIGHT);

        populateTable();
        register.getProductsTable().setEnabled(false);

        addListeners();
    }

    private void populateTable() {
        try {
            List<Purchase> purchaseList = purchaseCsvRepository.findAllByEmployee(register.getEmployee());
            model.addColumn("Produto");
            model.addColumn("Preço");
            model.addColumn("Quantidade");
            model.addColumn("Data");
            purchaseList.forEach(purchase -> {
                model.addRow(new Object[]{purchase.getDescription(), BigDecimalUtils.toPrettyWithRealSymbolString(purchase.getPrice()), purchase.getQuantity(), purchase.getDateFormatted()});
            });
        } catch (CsvParserException e) {
            throw new RuntimeException(e);
        }
        register.getProductsTable().setModel(model);
    }

    private void setDefaultFrame(JFrame frame) {
        frame.setContentPane(register.getPanel2());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Fiado - " + register.getEmployee());
    }

    private void addListeners() {
        register.getRegisterButton().addActionListener(registerButtonListener());
    }

    private ActionListener registerButtonListener() {
        return e -> {
            Purchase purchase = new Purchase();
            setPurchaseInRegister(purchase);

            try {
                purchaseCsvRepository.save(purchase);
            } catch (CsvRepositoryException ex) {
                throw new RuntimeException(ex); // FIXME: Show panel
            }

            model.addRow(new Object[]{purchase.getDescription(), BigDecimalUtils.toPrettyWithRealSymbolString(purchase.getPrice()), purchase.getQuantity(), purchase.getDateTime()});
            register.cleanAllFields();
        };
    }

    private void setPurchaseInRegister(Purchase purchase) {
        purchase.setEmployee(register.getEmployee());
        purchase.setDescription(register.getProductField().getText());
        purchase.setPrice(bigDecimalFromCurrencyString(register.getPriceField().getText()));
        purchase.setQuantity(((int) register.getProductQuantitySpinner().getValue()));
        purchase.setDateTime(LocalDateTime.now());
    }

}
