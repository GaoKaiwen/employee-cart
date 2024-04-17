package repository.csv;

import exception.CsvRepositoryException;
import model.Employee;

import java.nio.file.Path;

public class EmployeeCsvRepository extends CsvRepository<Employee> {

    public EmployeeCsvRepository() throws CsvRepositoryException {
        super(Path.of("employee"), Employee.class);
    }
}
