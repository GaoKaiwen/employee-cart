package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import exception.CsvParserException;
import model.ProductModel;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.String.format;

public class CsvParserUtils {

    public static <T> void addContentToFile(T content, Path filePath) throws CsvParserException {
        Path file = createFileIfItDoesNotExists(filePath);
        if(isFileEmpty(file)) {
            writeCsv(file, content);
        } else {
            appendCsv(file, content);
        }
    }

    public static Path createFileIfItDoesNotExists(Path filePath) throws CsvParserException {
        try {
            filePath.toFile().createNewFile();
        } catch (IOException e) {
            throw new CsvParserException(format("Failed to create file [%s].", filePath), e);
        }
        return filePath;
    }

    public static boolean isFileEmpty(Path filePath) throws CsvParserException {
        return getCsvHeaderFromFile(filePath) == null;
    }

    public static String[] getCsvHeaderFromFile(Path filePath) throws CsvParserException {
        try(CSVReader csvReader = new CSVReader(new FileReader(filePath.toFile()))) {
            return csvReader.readNext();
        } catch (FileNotFoundException e) {
            throw new CsvParserException(format("File [%s] not found.", filePath), e);
        } catch (IOException | CsvValidationException e) {
            throw new CsvParserException(format("Failed to read file [%s].", filePath), e);
        }
    }

    public static <T> void writeCsv(Path filePath, T content) throws CsvParserException {
        try(FileWriter fileWriter = new FileWriter(filePath.toFile())) {
            new StatefulBeanToCsvBuilder<T>(fileWriter)
                    .withApplyQuotesToAll(true)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withMappingStrategy(createHeaderColumnNameMappingStrategy(content))
                    .build()
                    .write(content);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CsvParserException(format("Failed to write content [%s] to CSV [%s].", content, ProductModel.class.getName()), e);
        }
    }

    public static <T> void appendCsv(Path filePath, T content) throws CsvParserException {
        String[] header = getCsvHeaderFromFile(filePath);
        try(FileWriter fileWriter = new FileWriter(filePath.toFile(), true)) {
            new StatefulBeanToCsvBuilder<T>(fileWriter)
                    .withApplyQuotesToAll(true)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withMappingStrategy(createNoHeaderMappingStrategy(content, header))
                    .build()
                    .write(content);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CsvParserException(format("Failed to append content [%s] to CSV [%s].", content, ProductModel.class.getName()), e);
        }
    }

    public static <T> List<T> getObjectsFromCsvFile(Path filePath, Class<T> contentClass) throws CsvParserException {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                    .withType(contentClass)
                    .build();
            return cb.parse();
        } catch (IOException e) {
            throw new CsvParserException(format("Failed to read file [%s].", filePath), e);
        }
    }

    private static <T> MappingStrategy<T> createHeaderColumnNameMappingStrategy(T content) {
        HeaderColumnNameMappingStrategy<T> headerColumnNameMappingStrategy = new HeaderColumnNameMappingStrategy<>();
        headerColumnNameMappingStrategy.setType((Class<T>)content.getClass());
        return headerColumnNameMappingStrategy;
    }

    private static <T> MappingStrategy<T> createNoHeaderMappingStrategy(T content, String[] header) {
        NoHeaderMappingStrategy<T> noHeaderMappingStrategy = new NoHeaderMappingStrategy<>(header);
        noHeaderMappingStrategy.setType((Class<T>) content.getClass());
        return noHeaderMappingStrategy;
    }

    // ======================================================================
    // Inner classes
    // ======================================================================

    private static class NoHeaderMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {

        String[] header;

        public NoHeaderMappingStrategy(String[] header) {
            this.header = header;
        }

        @Override
        public String[] generateHeader(T bean) {
            headerIndex.initializeHeaderIndex(header);
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
    }
}
