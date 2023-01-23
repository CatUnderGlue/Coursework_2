package Tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MonthlyTask extends Task implements Repeatable{
    public MonthlyTask(String title, String description, Type type, LocalDateTime dayOfCompletion) throws IncorrectArgumentException {
        super(title, description, type, dayOfCompletion);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        if (getDayOfCompletion().isAfter(date.atStartOfDay())){
            return false;
        } else {
            return date.getDayOfMonth() == getDayOfCompletion().getDayOfMonth();
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
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().plus(1), getDayOfCompletion().getDayOfMonth());
    }

    @Override
    public String toString() {
        return super.toString() + "Ежемесячная\n" +
                "Следующее повторение: " + getNextDay() + "\n";
    }
}
