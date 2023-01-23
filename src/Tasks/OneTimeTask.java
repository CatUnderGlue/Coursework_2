package Tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OneTimeTask extends Task {
    public OneTimeTask(String title, String description, Type type, LocalDateTime dateTime) throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    @Override
    public boolean appearsIn(LocalDate date) {
        return getDayOfCompletion().toLocalDate().equals(date);
    }

    @Override
    public String toString() {
        return super.toString() + "Одноразовая\n" +
                "День выполнения: " + getInitialDateTime() + "\n";
    }
}
