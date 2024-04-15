package service;

import exception.CsvParserException;
import model.EmployeeModel;
import model.ProductModel;
import utils.CsvParserUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    private final String EMPLOYEES_PATH = "src/main/java/utils/names";

    public void saveProduct(ProductModel product, String employee) throws CsvParserException {
        CsvParserUtils.addContentToFile(product, employee);
    }

    public List<EmployeeModel> readEmployeesInFile() throws CsvParserException {
        File file = new File(EMPLOYEES_PATH);
        return CsvParserUtils.getObjectsFromCsvFile(file, EmployeeModel.class);
    }

    public boolean saveEmployee(String name) throws IOException, CsvParserException {
        EmployeeModel employee = new EmployeeModel();
        employee.setName(name);
        CsvParserUtils.addContentToFile(employee, EMPLOYEES_PATH);
        return true;
    }
}
