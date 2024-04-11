package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class BigDecimalUtils {

    public static BigDecimal bigDecimalFromCurrencyString(String amount) {
        String amountWithoutPeriods = removePeriods(amount);
        String amountWithPeriodDecimalSeparator = replacePeriodWithComma(amountWithoutPeriods);
        return new BigDecimal(amountWithPeriodDecimalSeparator);
    }

    public static String toPrettyString(BigDecimal amount) {
        return formatBigDecimal(amount);
    }

    public static String toPrettyWithRealSymbolString(BigDecimal amount) {
        return "R$ " + formatBigDecimal(amount);
    }

    private static String removePeriods(String amount) {
        return amount.replace(".", "");
    }

    private static String replacePeriodWithComma(String amount) {
        return amount.replace(",", ".");
    }

    private static String formatBigDecimal(BigDecimal amount) {
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return decimalFormat.format(amount.doubleValue());
    }
}
