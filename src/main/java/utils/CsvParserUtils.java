package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import exception.CsvParserException;
import model.ProductModel;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.String.format;

public class CsvParserUtils {

    public static <T> void addContentToFile(T content, String filename) throws CsvParserException {
        File file = createFileIfItDoesNotExists(filename);
        if(isFileEmpty(file)) {
            writeCsv(file, content);
        } else {
            appendCsv(file, content);
        }
    }

    public static File createFileIfItDoesNotExists(String filename) throws CsvParserException {
        File file = new File(filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CsvParserException(format("Failed to create file [%s].", file), e);
        }
        return file;
    }

    public static boolean isFileEmpty(File file) throws CsvParserException {
        return getHeaderFromFile(file) == null;
    }

    public static String[] getHeaderFromFile(File file) throws CsvParserException {
        try(CSVReader csvReader = new CSVReader(new FileReader(file))) {
            return csvReader.readNext();
        } catch (FileNotFoundException e) {
            throw new CsvParserException(format("File [%s] not found.", file), e);
        } catch (IOException | CsvValidationException e) {
            throw new CsvParserException(format("Failed to read file [%s].", file), e);
        }
    }

    public static <T> void writeCsv(File file, T content) throws CsvParserException {
        try(FileWriter fileWriter = new FileWriter(file)) {
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

    public static <T> void appendCsv(File file, T content) throws CsvParserException {
        String[] header = getHeaderFromFile(file);
        try(FileWriter fileWriter = new FileWriter(file, true)) {
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
