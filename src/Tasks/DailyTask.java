package Tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DailyTask extends Task implements Repeatable{
    public DailyTask(String title, String description, Type type, LocalDateTime dateTime) throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        return getDayOfCompletion().isBefore(date.atStartOfDay());
    }

    @Override
    public LocalDate getNextDay() {
        if (getDayOfCompletion().isAfter(LocalDateTime.now()) || getDayOfCompletion().equals(LocalDateTime.now())){
            return getDayOfCompletion().toLocalDate();
        }
        if (getDayOfCompletion().getDayOfMonth() == LocalDate.now().getDayOfMonth() && getDayOfCompletion().getHour() > LocalTime.now().getHour()){
            return LocalDate.now();
        }
        return LocalDate.now().plusDays(1);
    }

    @Override
    public String toString() {
        return super.toString() + "Ежедневная\n" +
                "Следующее повторение: " + getNextDay() + "\n";
    }
}
