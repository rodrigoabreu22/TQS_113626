package org.tqs.deti.ua;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static LocalDate localDateFromDateParts(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDate isoTextToLocalDate(String isoText) {
        return LocalDate.parse(isoText, DateTimeFormatter.ISO_DATE);
    }
}
