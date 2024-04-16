package repository.csv;

import model.Employee;

import java.nio.file.Path;

public class EmployeeCsvRepository extends CsvRepository<Employee> {

    public EmployeeCsvRepository() {
        super(Path.of("employee"), Employee.class);
    }
}
