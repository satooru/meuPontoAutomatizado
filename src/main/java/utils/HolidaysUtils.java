package utils;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class HolidaysUtils {
    private static ResourceBundle holidays = ResourceBundle.getBundle("holidays");

    public static LocalDate checkHolidays(LocalDate localDate) {
        for (String date : holidays.getString("holidays").split(";")) {
            if (LocalDate.parse(date).compareTo(localDate) == 0)
                throw new RuntimeException("dia " + localDate.toString() + " e feriado");
        }
        return localDate;
    }
}
