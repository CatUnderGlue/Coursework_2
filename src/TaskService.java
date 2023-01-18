import Exceptions.IncorrectArgumentException;
import Tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskService {
    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private ArrayList<Task> removedTasks = new ArrayList<>();

    public TaskService() {
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
