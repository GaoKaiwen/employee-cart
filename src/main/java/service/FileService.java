package service;

import model.ProductModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileService {

    public void saveService() {}

    public void saveProduct(ProductModel product, String employee) throws IOException {
        saveProductInFile(product, employee);
    }

    private void saveProductInFile(ProductModel product, String employee) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(employee, true));
        bw.append(product.getDescription() + " - " + product.getPrice() + " - " + product.getQuantity() + "\n");
        bw.close();
    }

    public List<String> readEmployeesInFile() throws IOException {
        Path path = Paths.get("src/main/java/utils/names.txt");
        List<String> employeesList = Files.readAllLines(path);
        return employeesList;
    }

    public boolean saveEmployee(String name) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/utils/names.txt", true));
        boolean saveSucceeded = false;
        if(!isEmployeeRegistered(name)) {
            bw.append(name + "\n");
            saveSucceeded = true;
        }
        bw.close();
        return saveSucceeded;
    }

    private boolean isEmployeeRegistered(String name) throws IOException {
        List<String> employees = readEmployeesInFile();
        return employees.stream()
                .anyMatch(employee -> employee.equals(name));
    }

}
