package Tasks;

import exceptions.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;


public abstract class Task {
    private static int idGenerator = 0;
    private final int id;
    private final Type type;
    private String title;
    private String description;
    private final String initialDateTime;
    private final LocalDateTime dayOfCompletion;

    public Task(String title, String description, Type type, LocalDateTime dayOfCompletion) throws IncorrectArgumentException {
        setTitle(title);
        setDescription(description);
        this.dayOfCompletion = dayOfCompletion;
        this.initialDateTime = LocalDate.now().toString();
        this.type = type;
        this.id = idGenerator;
        idGenerator += 1;
    }

    public abstract boolean appearsIn(LocalDate date);

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setTitle(String title) throws IncorrectArgumentException {
        if (title == null || title.isEmpty() || title.isBlank()){
            throw new IncorrectArgumentException("Введите корректное название задачи");
        } else {
            this.title = title;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) throws IncorrectArgumentException {
        if (description == null || description.isEmpty() || description.isBlank()){
            throw new IncorrectArgumentException("Введите корректное описание задачи");
        } else {
            this.description = description;
        }
    }

    public String getDescription() {
        return description;
    }

    public String getInitialDateTime() {
        return initialDateTime;
    }

    public LocalDateTime getDayOfCompletion() {
        return dayOfCompletion;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + " Задача: " + getTitle() + "\n" +
                "Описание: " + getDescription() + "\n" +
                "Тип: " + getType() + ". Дата создания задачи: " + getInitialDateTime() + "\n" +
                "Время выполнения: " + getDayOfCompletion().toLocalTime() + "\n";
    }


}
