package repository.csv;

import model.ProductModel;

import java.nio.file.Path;

public class ProductCsvRepository extends CsvRepository<ProductModel> {

    public ProductCsvRepository(String employee) {
        super(resolvePathWithEmployee(employee), ProductModel.class);
    }

    private static Path resolvePathWithEmployee(String employee) {
        if(employee == null) {
            return BASE_FILE_PATH;
        }
        return BASE_FILE_PATH.resolve(employee);
    }
}
