import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.IntSupplier;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.Arrays;

public class LambdaDateOperations {
    public static void main(String[] args) {
        // 1
        System.out.println("Завдання 1:\n");
        Predicate<Integer> isLeapYear = year ->
                (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        System.out.println("2024 високосний? " + isLeapYear.test(2024));
        System.out.println("2023 високосний? " + isLeapYear.test(2023));

        BiFunction<LocalDate, LocalDate, Long> daysBetween = (date1, date2) ->
                ChronoUnit.DAYS.between(date1, date2);
        System.out.println("Днів між 01.01.2024 та 10.01.2024: " +
                daysBetween.apply(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 10)));

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

        Function<LocalDate, String> dayOfWeek = date ->
                date.getDayOfWeek().toString();
        System.out.println("20 липня 1969 року був " + dayOfWeek.apply(LocalDate.of(1969, 7, 20)));

        // 2
        System.out.println("\nЗавдання 2:\n");
        int[] nums = {12, 45, 78, 23};
        System.out.println("Масив чисел: " + Arrays.toString(nums) + "\n");

        IntSupplier maxOfFour = () -> IntStream.of(nums).max().orElseThrow();
        System.out.println("Максимальне значення: " + maxOfFour.getAsInt());

        IntSupplier minOfFour = () -> IntStream.of(nums).min().orElseThrow();
        System.out.println("Мінімальне значення: " + minOfFour.getAsInt() + "\n");


        // 3
        System.out.println("\nЗавдання 3:\n");
        int[] numbers = {1, -5, 10, 20, -3, 10, 15, 7};
        System.out.println("Масив чисел: " + Arrays.toString(numbers) + "\n");
        
        System.out.println("Сума чисел, що дорівнюють 10: " + sumByCondition(numbers, n -> n == 10));
        System.out.println("Сума чисел поза діапазоном 5-15: " + sumByCondition(numbers, n -> n < 5 || n > 15));
        System.out.println("Сума додатних чисел: " + sumByCondition(numbers, n -> n > 0));
        System.out.println("Сума від'ємних чисел: " + sumByCondition(numbers, n -> n < 0) + "\n");
    }

    public static int sumByCondition(int[] array, IntPredicate condition) {
        return IntStream.of(array).filter(condition).sum();
    }
}