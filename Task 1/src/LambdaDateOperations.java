import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaDateOperations {
    public static void main(String[] args) {

        System.out.println("\n№1");
        Predicate<Integer> isLeapYear = year ->
                (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

        System.out.println("2024 високосний? " + isLeapYear.test(2024));
        System.out.println("2023 високосний? " + isLeapYear.test(2023));

        
        System.out.println("\n№2");
        BiFunction<LocalDate, LocalDate, Long> daysBetween = (date1, date2) ->
                ChronoUnit.DAYS.between(date1, date2);

        System.out.println("Днів між 01.01.2024 та 10.01.2024: " +
                daysBetween.apply(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10)));

        
        System.out.println("\n№3");
        BiFunction<LocalDate, LocalDate, Long> fullSundaysBetween = (date1, date2) -> {
            long count = 0;
            LocalDate tempDate = date1;
            while (!tempDate.isAfter(date2)) {
                if (tempDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    count++;
                }
                tempDate = tempDate.plusDays(1);
            }
            return count;
        };

        System.out.println("Кількість неділь між 01.01.2024 та 31.01.2024: " +
                fullSundaysBetween.apply(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)));

        
        System.out.println("\n№4");
        Function<LocalDate, String> dayOfWeek = date ->
                date.getDayOfWeek().toString();

        System.out.println("20 липня 1969 року був " + dayOfWeek.apply(LocalDate.of(1969, 7, 20)));
    }
}
