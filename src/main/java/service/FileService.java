package service;

import model.ProductModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public void saveService() {}

    public void saveProduct(ProductModel product, String employee) {
        saveProductInFile(product, employee);
    }

    private void saveProductInFile(ProductModel product, String employee) {

    }

    public List<String> readEmployeesInFile() throws IOException {
        Path path = Paths.get("src/main/java/utils/names.txt");
        List<String> employeesList = Files.readAllLines(path);
        return employeesList;
    }

}
