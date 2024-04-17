package repository.csv;

import exception.CsvParserException;
import exception.CsvRepositoryException;
import model.Purchase;
import utils.CsvParserUtils;

import java.nio.file.Path;
import java.util.List;

public class PurchaseCsvRepository extends CsvRepository<Purchase> {

    private static final String COLUMN_EMPLOYEE = "EMPLOYEE";
    public PurchaseCsvRepository() throws CsvRepositoryException {
        super(Path.of("purchase"), Purchase.class);
    }

    public List<Purchase> findAllByEmployee(String employee) throws CsvParserException {
        return CsvParserUtils.getObjectsFromCsvFile(super.getFilePath(), getEntityType(), CsvParserUtils.csvBeanFilterByColumn(COLUMN_EMPLOYEE, employee, getFilePath()));
    }
}
