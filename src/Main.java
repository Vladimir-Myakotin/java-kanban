import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import manager.TaskManager;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = new TaskManager();

        // Создаем задачи
        Task task1 = new Task("Купить продукты", "Составить список", Status.NEW);
        Task task2 = new Task("Уборка квартиры", "Помыть пол", Status.IN_PROGRESS);
        Task addedTask1 = taskManager.addTask(task1);
        Task addedTask2 = taskManager.addTask(task2);


        // Добавляем эпик
        Epic epic = new Epic(0, "Переезд", "Собрать вещи", Status.NEW);
        Epic addedEpic = taskManager.addEpic(epic);
        System.out.println("Added epic ID: " + addedEpic.getId());

        // Добавляем подзадачу к этому эпику
        Subtask subtask = new Subtask(0, "Переезд", "Упаковать одежду", addedEpic.getId(), Status.NEW);
        Subtask addedSubtask = taskManager.addSubtask(subtask);
        System.out.println("Added subtask ID: " + addedSubtask.getId());
        System.out.println("Subtasks for epic " + addedEpic.getId() + ": " + taskManager.getSubtasksForEpic(addedEpic.getId()));
        Subtask subtask1 = new Subtask(0, "Упаковать книги", "Собрать книги в коробки", addedEpic.getId(), Status.NEW);
        Subtask subtask2 = new Subtask(0, "Упаковать посуду", "Собрать посуду в коробки", addedEpic.getId(), Status.NEW);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        // Обновляем подзадачу
        addedSubtask.setStatus(Status.DONE);  // Обновляем статус подзадачи
        taskManager.updateTask(addedSubtask);  // Обновляем задачу в TaskManager
        System.out.println("Updated subtask status: " + addedSubtask.getStatus());

        // Обновляем эпик
        addedEpic.setDescription("Переезд в новый дом");
        taskManager.updateTask(addedEpic);  // Обновляем эпик
        System.out.println("Updated epic description: " + addedEpic.getDescription());
        System.out.println("Epic status after update: " + taskManager.getTaskById(addedEpic.getId()).getStatus());

        //Обновление задачи
        addedTask2.setName("Генеральная уборка квартиры");
        addedTask2.setDescription("Помыть пол, протереть пыль");
        addedTask1.setStatus(Status.DONE);

        // Получение задач по ID
        Task retrievedTask = taskManager.getTaskById(addedTask1.getId());
        System.out.println("Полученная задача: " + retrievedTask);

        System.out.println("------------------------");

        //Получение всех задач, эпиков и подзадач
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        System.out.println("----------------------");

        //Получение задач одного эпика
        List<Subtask> epicSubtasks = taskManager.getSubtasksForEpic(addedEpic.getId());
        System.out.println("Subtasks for epic " + addedEpic.getId() + ": " + epicSubtasks);

        // Удаляем задачу, эпик и подзадачу по ID
        taskManager.deleteTaskById(addedTask1.getId());
        taskManager.deleteEpicById(addedEpic.getId());


        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        //Удаление всех задач, эпиков и подзадач
        taskManager.clearTask();
        taskManager.clearEpic();
        taskManager.clearSubtask();

        System.out.println("---------------------");

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

    }
}
