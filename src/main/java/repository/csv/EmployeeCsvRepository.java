package repository.csv;

import model.EmployeeModel;

public class EmployeeCsvRepository extends CsvRepository<EmployeeModel> {

    public EmployeeCsvRepository() {
        super(BASE_FILE_PATH.resolve("names"), EmployeeModel.class);
    }
}
