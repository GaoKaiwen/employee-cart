package service;

import exception.CsvRepositoryException;
import gui.Main;
import gui.Register;
import model.EmployeeModel;
import repository.csv.EmployeeCsvRepository;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class MainService {

    private final Main main;
    private final EmployeeCsvRepository employeeCsvRepository;

    public MainService(Main main) {
        this.main = main;
        employeeCsvRepository = new EmployeeCsvRepository();
    }

    public void createWindow() throws CsvRepositoryException {
        JFrame frame = new JFrame("Main");
        setDefaultFrame(frame);
        fillEmployeesBox();

        addListeners(frame);
    }

    private void fillEmployeesBox() throws CsvRepositoryException {
        main.getNameCBox().removeAllItems();
        List<EmployeeModel> employeesList = employeeCsvRepository.findAll();
        employeesList.forEach(employee -> main.getNameCBox().addItem(employee.getName()));
    }

    private void setDefaultFrame(JFrame frame) {
        frame.setContentPane(main.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("Fiado");
    }

    private void addListeners(JFrame frame) {
        main.getSelectButton().addActionListener(selectButtonListener(frame));
        main.getSignUpButton().addActionListener(signUpButtonListener());
    }

    private ActionListener selectButtonListener(JFrame frame) {
        return e -> {
            frame.dispose();
            Register register = new Register(Objects.requireNonNull(main.getNameCBox().getSelectedItem()).toString());
            RegisterService registerService = new RegisterService(register);
            registerService.createWindow();
        };
    }

    private ActionListener signUpButtonListener() {
        return e -> {
            String name = JOptionPane.showInputDialog("Digite seu nome");
            if (!(name == null) && !name.isBlank()) {
                try {
                    EmployeeModel employeeModel = new EmployeeModel();
                    employeeModel.setName(name);
                    employeeCsvRepository.save(employeeModel);
                    JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                    fillEmployeesBox();
                } catch (CsvRepositoryException ex) {
                    JOptionPane.showMessageDialog(null, "Usuário já cadastrado!", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

}
