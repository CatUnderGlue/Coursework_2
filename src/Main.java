import Tasks.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static TaskService taskService = new TaskService();
    public static Scanner sc = new Scanner(System.in);

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
                            inputTask();
                            break;
                        case 2:
                            deleteTask();
                            break;
                        case 3:
                            printAllTasksPerDay();
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

    private static void inputTask() {
        try {
            // Получаем данные для создания задачи
            String title = getTitle();
            String description = getDescription();
            int taskType = getTaskType();
            Task.Type type = taskType == 1 ? Task.Type.WORK : Task.Type.PERSONAL;
            int repeatType = getRepeatType();
            LocalDateTime dateTime = getDayOfCompletion();
            // Создаём задачу
            Task task = taskService.createTask(title, description, type, dateTime, repeatType);
            taskService.addTask(task);
            System.out.println("Задача успешно добавлена!");
            System.out.println(task);
            sc.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void deleteTask() {
        System.out.print("Введите уникальный номер задачи: ");
        int id = sc.nextInt();
        try {
            taskService.removeTask(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printAllTasksPerDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.print("Введите дату для получения задач в формате 2022-12-31: ");
        String dayOfCompletion = sc.next();
        if (!checkDayValidity(dayOfCompletion)) {
            printAllTasksPerDay();
        }
        LocalDateTime date = LocalDateTime.parse(dayOfCompletion + " 23:59", formatter);
        List<Task> tasks = taskService.getAllByDate(date);
        if (tasks.isEmpty()) {
            System.out.println("Задачи на указанный день не найдены.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    public static String getTitle() {
        String title;
        do {
            System.out.print("Введите название задачи: ");
            title = sc.nextLine();
        } while (title == null || title.isEmpty() || title.isBlank());
        return title;
    }

    public static String getDescription() {
        String description;
        do {
            System.out.print("Введите описание задачи: ");
            description = sc.nextLine();
        } while (description == null || description.isEmpty() || description.isBlank());
        return description;
    }

    public static int getTaskType() {
        int taskType;
        do {
            System.out.print("Выберите тип задачи 1 - Рабочая, 2 - Личная : ");
            taskType = sc.nextInt();
        } while (taskType > 2 || taskType < 1);
        return taskType;
    }

    public static int getRepeatType() {
        int repeatType;
        do {
            System.out.print("Выберите повторяемость задачи 1 - Одноразовая, 2 - Ежедневная, 3 - Еженедельная, 4 - Ежемесячная, 5 - Ежегодная: ");
            repeatType = sc.nextInt();
        } while (repeatType > 5 || repeatType < 1);
        return repeatType;
    }

    public static LocalDateTime getDayOfCompletion() {
        LocalDate day = getDay();
        LocalTime time = getTime();
        return LocalDateTime.of(day, time);
    }

    public static LocalDate getDay() {
        String dayOfCompletion;
        do {
            System.out.print("Введите дату выполнения в формате 2022-12-31: ");
            dayOfCompletion = sc.next();
        } while (!checkDayValidity(dayOfCompletion));
        return LocalDate.parse(dayOfCompletion);
    }

    public static LocalTime getTime() {
        String timeOfCompletion;
        do {
            System.out.print("Введите время выполнения в формате 23:59: ");
            timeOfCompletion = sc.next();
        } while (!checkTimeValidity(timeOfCompletion));
        return LocalTime.parse(timeOfCompletion);
    }

    private static boolean checkDayValidity(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean flag = false;
        try {
            LocalDate d = LocalDate.parse(date, formatter);
            flag = true;
        } catch (Exception e) {
            System.out.println("Некорректный ввод даты! Указывайте дату строго по указанному формату!");
        }
        return flag;
    }

    private static boolean checkTimeValidity(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        boolean flag = false;
        try {
            LocalTime t = LocalTime.parse(time, formatter);
            flag = true;
        } catch (Exception e) {
            System.out.println("Некорректный ввод времени! Указывайте время строго по указанному формату!");
        }
        return flag;
    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n0. Выход");
    }
}