package service;

import exception.CsvParserException;
import model.ProductModel;
import utils.CsvParserUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileService {

    public void saveProduct(ProductModel product, String employee) throws CsvParserException {
        CsvParserUtils.addContentToFile(product, employee);
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
