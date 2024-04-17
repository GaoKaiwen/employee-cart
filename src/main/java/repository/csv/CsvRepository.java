package repository.csv;

import exception.CsvParserException;
import exception.CsvRepositoryException;
import repository.Repository;
import utils.CsvParserUtils;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import static java.lang.String.format;

public abstract class CsvRepository<T> implements Repository<T> {

    private Path filePath;
    private Class<T> entityType;

    protected CsvRepository(Path filePath, Class<T> entityType) throws CsvRepositoryException {
        this.filePath = filePathFrom(filePath);
        this.entityType = entityType;
    }

    public void save(T entity) throws CsvRepositoryException {
        try {
            CsvParserUtils.addContentToFile(entity, filePath);
        } catch (CsvParserException e) {
            throw new CsvRepositoryException(format("Failed to save entity [%s].", entityType.getName()), e);
        }
    }

    public List<T> findAll() throws CsvRepositoryException {
        try {
            return CsvParserUtils.getObjectsFromCsvFile(filePath, entityType);
        } catch (CsvParserException e) {
            throw new CsvRepositoryException(format("Failed to find all [%s] entities.", entityType.getName()), e);
        }
    }

    private Path filePathFrom(Path filePath) throws CsvRepositoryException {
        URL resource = CsvRepository.class.getClassLoader().getResource(filePath.toString());
        try {
            return Path.of(resource.toURI());
        } catch (URISyntaxException e) {
            throw new CsvRepositoryException(format("Failed to get URI [%s].", resource), e);
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public Class<T> getEntityType() {
        return entityType;
    }

    public void setEntityType(Class<T> entityType) {
        this.entityType = entityType;
    }
}
