package Tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class WeeklyTask extends Task implements Repeatable{
    public WeeklyTask(String title, String description, Type type, LocalDateTime dayOfCompletion) throws IncorrectArgumentException {
        super(title, description, type, dayOfCompletion);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        if (getDayOfCompletion().isAfter(date.atStartOfDay())){
            return false;
        } else {
            return date.getDayOfWeek().getValue() == getDayOfCompletion().getDayOfWeek().getValue();
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
        return LocalDate.now().with(TemporalAdjusters.next(getDayOfCompletion().getDayOfWeek()));
    }

    @Override
    public String toString() {
        return super.toString() + "Еженедельная\n" +
                "Следующее повторение: " + getNextDay() + "\n";
    }
}
