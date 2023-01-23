import exceptions.IncorrectArgumentException;
import Tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static TaskService taskService = new TaskService();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            deleteTask(scanner);
                            break;
                        case 3:
                            printAllTasksPerDay(scanner);
                            break;
                        case 4:
                            printAllDeletedTasks();
                            break;
                        case 5:
                            updateTask(scanner);
                            break;
                        case 6:
                            printAllTasksSortedByDate();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        try {
            // Получаем данные для создания задачи
            String title = getTitle(scanner);
            String description = getDescription(scanner);
            int taskType = getTaskType(scanner);
            Type type = taskType == 1 ? Type.WORK : Type.PERSONAL;
            int repeatType = getRepeatType(scanner);
            LocalDateTime dateTime = getDayOfCompletion(scanner);
            // Создаём задачу
            Task task = taskService.createTask(title, description, type, dateTime, repeatType);
            taskService.addTask(task);
            System.out.println("Задача успешно добавлена!");
            System.out.println(task);
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteTask(Scanner scanner) {
        int id = getId(scanner);
        try {
            taskService.removeTask(id);
            System.out.println("Задача успешно удалена.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printAllTasksPerDay(Scanner scanner) {
        System.out.print("Введите дату для получения задач в формате 2022-12-31: ");
        String dayOfCompletion = scanner.next();
        if (!checkDayValidity(dayOfCompletion)) {
            printAllTasksPerDay(scanner);
            return;
        }
        LocalDate date = LocalDate.parse(dayOfCompletion, dateFormatter);
        List<Task> tasks = taskService.getAllTasksByDate(date);
        if (tasks.isEmpty()) {
            System.out.println("Задачи на указанный день не найдены.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void printAllTasksSortedByDate(){
        Map<LocalDate, List<Task>> tasks = taskService.getAllTasksSortedByDate();
        for (LocalDate date : tasks.keySet()){
            System.out.println("На дату: " + date);
            for (Task task : tasks.get(date)){
                System.out.println(task);
            }
        }
    }

    private static void printAllDeletedTasks(){
        System.out.println("Удалённые задачи:");
        List<Task> tasks = taskService.getRemovedTasks();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private static void updateTask(Scanner scanner){
        int id = getId(scanner);
        try {
            Task task = taskService.getTaskPerId(id);
            String title = getTitle(scanner);
            String description = getDescription(scanner);
            task.setTitle(title);
            task.setDescription(description);
            System.out.printf("Задача под номером %d успешно обновлена.\n", id);
        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getTitle(Scanner scanner) {
        String title;
        System.out.print("Введите название задачи: ");
        do {
            title = scanner.nextLine();
        } while (title == null || title.isEmpty() || title.isBlank());
        return title;
    }
    private static int getId(Scanner scanner) {
        int id;
        do {
            System.out.print("Введите уникальный номер задачи: ");
            id = scanner.nextInt();
        } while (id < 0);
        return id;
    }

    private static String getDescription(Scanner scanner) {
        String description;
        System.out.print("Введите описание задачи: ");
        do {
            description = scanner.nextLine();
        } while (description == null || description.isEmpty() || description.isBlank());
        return description;
    }

    private static int getTaskType(Scanner scanner) {
        int taskType;
        System.out.print("Выберите тип задачи 1 - Рабочая, 2 - Личная : ");
        do {
            taskType = scanner.nextInt();
        } while (taskType > 2 || taskType < 1);
        return taskType;
    }

    private static int getRepeatType(Scanner scanner) {
        int repeatType;
        System.out.print("Выберите повторяемость задачи 1 - Одноразовая, 2 - Ежедневная, 3 - Еженедельная, 4 - Ежемесячная, 5 - Ежегодная: ");
        do {
            repeatType = scanner.nextInt();
        } while (repeatType > 5 || repeatType < 1);
        return repeatType;
    }

    private static LocalDateTime getDayOfCompletion(Scanner scanner) {
        LocalDate day = getDay(scanner);
        LocalTime time = getTime(scanner);
        return LocalDateTime.of(day, time);
    }

    private static LocalDate getDay(Scanner scanner) {
        String dayOfCompletion;
        System.out.print("Введите дату выполнения в формате 2022-12-31: ");
        do {
            dayOfCompletion = scanner.next();
        } while (!checkDayValidity(dayOfCompletion));
        return LocalDate.parse(dayOfCompletion);
    }

    private static LocalTime getTime(Scanner scanner) {
        String timeOfCompletion;
        System.out.print("Введите время выполнения в формате 23:59: ");
        do {
            timeOfCompletion = scanner.next();
        } while (!checkTimeValidity(timeOfCompletion));
        return LocalTime.parse(timeOfCompletion);
    }

    private static boolean checkDayValidity(String date) {
        boolean flag = false;
        try {
            LocalDate d = LocalDate.parse(date, dateFormatter);
            flag = true;
        } catch (Exception e) {
            System.out.println("Некорректный ввод даты! Указывайте дату строго по указанному формату!");
        }
        return flag;
    }

    private static boolean checkTimeValidity(String time) {

        boolean flag = false;
        try {
            LocalTime t = LocalTime.parse(time, timeFormatter);
            flag = true;
        } catch (Exception e) {
            System.out.println("Некорректный ввод времени! Указывайте время строго по указанному формату!");
        }
        return flag;
    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n" +
                "4. Получить все удалённые задачи\n5. Изменить название и описание существующей задачи\n" +
                "6. Получить все задачи отсортерованные по дате.\n0. Выход");
    }
}