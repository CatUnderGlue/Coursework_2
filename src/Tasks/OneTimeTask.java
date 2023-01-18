package Tasks;

import Exceptions.IncorrectArgumentException;

import java.time.LocalDateTime;

public class OneTimeTask extends Task {
    public OneTimeTask(String title, String description, Type type, LocalDateTime dateTime) throws IncorrectArgumentException {
        super(title, description, type, dateTime);
    }

    @Override
    public boolean appearsIn(LocalDateTime date) {
        return getDayOfCompletion().toLocalDate().equals(date.toLocalDate());
    }
}
