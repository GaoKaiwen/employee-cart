package service;

import exception.CsvParserException;
import exception.CsvRepositoryException;
import gui.Register;
import model.Purchase;
import repository.csv.PurchaseCsvRepository;
import utils.BigDecimalUtils;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
        try {
            this.purchaseCsvRepository = new PurchaseCsvRepository();
        } catch (CsvRepositoryException e) {
            throw new RuntimeException(e); // TODO: Handle exception
        }
        this.model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public void createWindow() {
        JFrame frame = new JFrame("Register");
        setDefaultFrame(frame);
        register.getProductField().setHorizontalAlignment(JTextField.RIGHT);

        populateTable();

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
        register.getCancelButton().addActionListener(cancelButtonListener());
    }

    private ActionListener cancelButtonListener() {
        return e -> {
            int selectedRow = register.getProductsTable().getSelectedRow();

            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!", "Excluir produto", JOptionPane.WARNING_MESSAGE);
            } else {
                String justification = JOptionPane.showInputDialog(null, "[" + model.getValueAt(selectedRow, 0) + "] " + model.getValueAt(selectedRow, 1) + "\nDigite o motivo do cancelamento:", "Justificativa", JOptionPane.PLAIN_MESSAGE);

                if(justification == null || justification.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Digite o motivo do cancelamento!", "Falha no cancelamento", JOptionPane.WARNING_MESSAGE);
                } else {
                    //Update purchase file
                }
            }
        };
    }

    private ActionListener registerButtonListener() {
        return e -> {
            if(register.getProductField().getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Digite o nome do produto!", "Produto sem descrição", JOptionPane.WARNING_MESSAGE);
            } else {
                Purchase purchase = new Purchase();
                setPurchaseInRegister(purchase);

                try {
                    purchaseCsvRepository.save(purchase);
                } catch (CsvRepositoryException ex) {
                    throw new RuntimeException(ex); // FIXME: Show panel
                }

                model.addRow(new Object[]{purchase.getDescription(), BigDecimalUtils.toPrettyWithRealSymbolString(purchase.getPrice()), purchase.getQuantity(), purchase.getDateFormatted()});
                register.cleanAllFields();
            }
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
