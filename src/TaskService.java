import exceptions.IncorrectArgumentException;
import Tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TaskService {
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final List<Task> removedTasks = new ArrayList<>();

    public Task createTask(String title, String description, Type type, LocalDateTime dateTime, int repeatType) throws IncorrectArgumentException {
        switch (repeatType) {
            case 1:
                return new OneTimeTask(title, description, type, dateTime);
            case 2:
                return new DailyTask(title, description, type, dateTime);
            case 3:
                return new WeeklyTask(title, description, type, dateTime);
            case 4:
                return new MonthlyTask(title, description, type, dateTime);
            case 5:
                return new YearlyTask(title, description, type, dateTime);
            default:
                return new OneTimeTask(title, description, type, dateTime);
        }
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void removeTask(int id) throws IncorrectArgumentException {
        if (taskMap.containsKey(id)) {
            removedTasks.add(taskMap.remove(id));
        } else {
            throw new IncorrectArgumentException("Задача под заданным номером отсутствует");
        }
    }

    public Task getTaskPerId(int id) throws IncorrectArgumentException {
        if (taskMap.containsKey(id)) {
            return taskMap.get(id);
        } else {
            throw new IncorrectArgumentException("Задача под заданным номером отсутствует");
        }
    }

    public List<Task> getAllTasksByDate(LocalDate dateTime) {
        List<Task> tasks = new ArrayList<>();
        for (Task task : taskMap.values()) {
            if (task.appearsIn(dateTime)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public Map<LocalDate, List<Task>> getAllTasksSortedByDate() {
        Map<LocalDate, List<Task>> tasksSortedByDate = new TreeMap<>();
        for (Task task : taskMap.values()) {
            if (!tasksSortedByDate.containsKey(task.getDayOfCompletion().toLocalDate())) {
                tasksSortedByDate.put(task.getDayOfCompletion().toLocalDate(), new ArrayList<>());
            }
            tasksSortedByDate.get(task.getDayOfCompletion().toLocalDate()).add(task);
        }
        return tasksSortedByDate;
    }

    public List<Task> getRemovedTasks() {
        List<Task> removedTasksTmp = this.removedTasks;
        return removedTasksTmp;
    }
}
