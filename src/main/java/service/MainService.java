package service;

import exception.CsvParserException;
import gui.Main;
import gui.Register;
import model.EmployeeModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainService {

    private final Main main;
    private final FileService fileService;

    public MainService(Main main) {
        this.main = main;
        fileService = new FileService();
    }

    public void createWindow() throws IOException, CsvParserException {
        JFrame frame = new JFrame("Main");
        setDefaultFrame(frame);
        fillEmployeesBox();

        addListeners(frame);
    }

    private void fillEmployeesBox() throws IOException, CsvParserException {
        main.getNameCBox().removeAllItems();
        List<EmployeeModel> employeesList = fileService.readEmployeesInFile();
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
            try {
                if(!(name == null) && !name.isBlank()) {
                    if(fileService.saveEmployee(name)) {
                        JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
                        fillEmployeesBox();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário já cadastrado!", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (CsvParserException ex) {
                //TODO: Handle CsvException
                throw new RuntimeException(ex);
            }
        };
    }

}
