import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import manager.TaskManager;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager manager = new TaskManager();

        // Создаем задачу
        Task task = new Task(0, "Сделать отчёт", "Подготовить отчёт", Status.NEW);
        Task addedTask = manager.addTask(task);

        Task task1 = new Task(0, "Переезд", "Переехать в новый дом", Status.NEW);
        Task addedTask1 = manager.addTask(task1);

        System.out.println(addedTask);
        System.out.println(addedTask1);

        // Обновление задачи
        Task updatedTask = new Task(1, "Упаковка вещей", "Упаковать вещи для переезда",
                Status.IN_PROGRESS);
        manager.updateTask(updatedTask);

        //Получение задачи по ID
        System.out.println(manager.getTaskById(2));

        //Удаление задачи по ID
        manager.deleteTask(1);

        // Создаем эпик
        Epic epic = manager.addEpic(new Epic(0, "Переезд в новый офис", "Планирование и выполнение переезда", Status.NEW));

        // Создаем подзадачи для эпика
        Subtask subtask1 = new Subtask(0, "Упаковка мебели", "Упаковать офисные столы и стулья",
                epic.getId(), Status.NEW);
        Subtask subtask2 = new Subtask(0, "Перевезти технику", "Перевезти компьютеры в новый офис",
                epic.getId(), Status.NEW);

        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        System.out.println("Epic: " + epic);
        System.out.println("Subtasks: ");

        List<Integer> subtaskIds = manager.getSubtasksForEpic(epic.getId());

        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = manager.getAllSubtasks().get(subtaskId);
            System.out.println(subtask);
        }

        System.out.println(manager.getTaskById(2));

        System.out.println("______________________");

        System.out.println(manager.getAllTasks());

        manager.clearAll();

        System.out.println("_____________________________");

        System.out.println(manager.getAllTasks());


    }
}