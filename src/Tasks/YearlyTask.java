package Tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class YearlyTask extends Task implements Repeatable{
    public YearlyTask(String title, String description, Type type, LocalDateTime dayOfCompletion) throws IncorrectArgumentException {
        super(title, description, type, dayOfCompletion);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        if (getDayOfCompletion().isAfter(date.atStartOfDay())){
            return false;
        } else {
            return date.getDayOfYear() == getDayOfCompletion().getDayOfYear();
        }
    }

    @Override
    public LocalDate getNextDay() {
        if (getDayOfCompletion().isAfter(LocalDateTime.now()) || getDayOfCompletion().equals(LocalDateTime.now())){
            return getDayOfCompletion().toLocalDate();
        }
        if (getDayOfCompletion().getDayOfMonth() == LocalDate.now().getDayOfMonth() && getDayOfCompletion().getHour() > LocalTime.now().getHour()){
            return LocalDate.now();
        }
        return LocalDate.of(LocalDate.now().getYear() + 1, getDayOfCompletion().getMonth(), getDayOfCompletion().getDayOfMonth());
    }

    @Override
    public String toString() {
        return super.toString() + "Ежегодная\n" +
                "Следующее повторение: " + getNextDay() + "\n";
    }
}
