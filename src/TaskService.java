import Exceptions.IncorrectArgumentException;
import Tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TaskService {
    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private ArrayList<Task> removedTasks = new ArrayList<>();

    public TaskService() {
    }

    public Task createTask(String title, String description, Task.Type type, LocalDateTime dateTime, int repeatType) throws IncorrectArgumentException {
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
                return null;
        }
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void removeTask(int id) throws IncorrectArgumentException {
        if (taskMap.containsKey(id)) {
            removedTasks.add(taskMap.get(id));
            taskMap.remove(id);
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

    public ArrayList<Task> getAllTasksByDate(LocalDateTime dateTime) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskMap.values()) {
            if (task.appearsIn(dateTime)) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    public Map<LocalDate, ArrayList<Task>> getAllTasksSortedByDate() {
        Map<LocalDate, ArrayList<Task>> tasksSortedByDate = new TreeMap<>();
        for (Task task : taskMap.values()){
            if (tasksSortedByDate.containsKey(task.getDayOfCompletion().toLocalDate())){
                tasksSortedByDate.get(task.getDayOfCompletion().toLocalDate()).add(task);
            } else {
                tasksSortedByDate.put(task.getDayOfCompletion().toLocalDate(), new ArrayList<>());
                tasksSortedByDate.get(task.getDayOfCompletion().toLocalDate()).add(task);
            }
        }
        return tasksSortedByDate;
    }

    public ArrayList<Task> getRemovedTasks() {
        return removedTasks;
    }
}
