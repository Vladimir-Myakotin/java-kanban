public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task(1,"Задача 1", "Описание задачи 1");
        task1.setStatusTask(Status.IN_PROGRESS);
        Task task2 = new Task( "Задача 2", "Описание задачи 2", Status.NEW);
        System.out.println(task1);
        task1.updateTask(task2);
        System.out.println(task1);
        System.out.println("-----");

        Subtask subtask = new Subtask(
                1,
                "Подзадача 1",
                "Описание подзадачи",
                Status.IN_PROGRESS,
                10 // ID
        );

        System.out.println(subtask);
        System.out.println("-----");

        Epic epic = new Epic("Эпик 1", "Описание эпика");

        Subtask subtask1 = new Subtask(
                1,
                "Подзадача 1",
                "Описание подзадачи 1",
                Status.IN_PROGRESS,
                epic.getIdTask()
        );

        Subtask subtask2 = new Subtask(
                2,
                "Подзадача 2",
                "Описание подзадачи 2",
                Status.NEW,
                epic.getIdTask()
        );

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);

        System.out.println(epic);
        System.out.println("--------");

        TaskManager taskManager = new TaskManager(
                1,
                "Задача менеджера",
                "Описание задачи менеджера",
                Status.IN_PROGRESS
        );

        System.out.println(taskManager);


        task1 = new Task("Задача 1", "Описание задачи 1");
        task2 = new Task("Задача 2", "Описание задачи 2");

        System.out.println(task1);
        System.out.println(task2);

    }
}