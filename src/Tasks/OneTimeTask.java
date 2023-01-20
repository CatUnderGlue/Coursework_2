package Tasks;

import Exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OneTimeTask extends Task {
    public OneTimeTask(String title, String description, Type type, LocalDateTime dateTime) throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    @Override
    public boolean appearsIn(LocalDateTime date) {
        return getDayOfCompletion().toLocalDate().equals(date.toLocalDate());
    }

    @Override
    public String toString() {
        return super.toString() + "Одноразовая\n" +
                "День выполнения: " + getInitialDateTime() + "\n";
    }
}
