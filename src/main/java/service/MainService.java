package service;

import gui.Main;
import gui.Register;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainService {

    private Main main;
    private FileService fileService;

    public MainService(Main main) {
        this.main = main;
        fileService = new FileService();
    }

    public void createWindow() throws IOException {
        JFrame frame = new JFrame("Main");
        setDefaultFrame(frame);
        fillEmployeesBox();

        addListeners(frame);
    }

    private void fillEmployeesBox() throws IOException {
        List<String> employeesList = fileService.readEmployeesInFile();
        employeesList.forEach(employee -> main.getNameCBox().addItem(employee));
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
    }

    private ActionListener selectButtonListener(JFrame frame) {
        return e -> {
            frame.dispose();
            Register register = new Register(Objects.requireNonNull(main.getNameCBox().getSelectedItem()).toString());
            RegisterService registerService = new RegisterService(register);
            registerService.createWindow();
        };
    }

}
