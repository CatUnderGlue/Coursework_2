import Exceptions.IncorrectArgumentException;
import Tasks.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void addTask(Task task){
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

    public ArrayList<Task> getAllByDate(LocalDateTime dateTime){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskMap.values()){
            if (task.appearsIn(dateTime)){
                tasks.add(task);
            }
        }
        return tasks;
    }
}
