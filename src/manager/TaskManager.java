package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.*;




public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, List<Integer>> epicSubtasks = new HashMap<>();
    private int nextId = 1;

    private int generateId() {
        return nextId++;
    }

    // Добавление новой задачи
    public Task addTask(Task task) {
        int id = generateId();
        Task newTask = new Task(task);
        newTask.setId(id);
        tasks.put(id, newTask);
        return newTask;
    }

    // Добавление нового эпика
    public Epic addEpic(Epic epic) {
        int id = generateId();
        Epic newEpic = new Epic(id, epic.getName(), epic.getDescription(), epic.getStatus());
        this.epics.put(id, newEpic);
        epicSubtasks.put(id, new ArrayList<>());
        return newEpic;
    }

    // Добавление новой подзадачи
    public Subtask addSubtask(Subtask subtask) {
        int id = generateId();
        Subtask newSubtask = new Subtask(subtask);
        newSubtask.setId(id);

        this.subtasks.put(id, newSubtask);

        List<Integer> subtaskList = epicSubtasks.getOrDefault(subtask.getEpicId(), new ArrayList<>());
        subtaskList.add(id);
        epicSubtasks.put(subtask.getEpicId(), subtaskList);

        updateEpicStatus(subtask.getEpicId());

        return newSubtask;
    }

    // Обновление задачи, эпика или Подзадачи
    public void updateTask(Task task) {
        if (task instanceof Epic) {
            epics.put(task.getId(), (Epic) task);
        } else if (task instanceof Subtask) {
            subtasks.put(task.getId(), (Subtask) task);
            updateEpicStatus(((Subtask) task).getEpicId());  // Обновляем статус эпика
        } else {
            tasks.put(task.getId(), task);
        }
    }


    // Получение задачи по ID
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        }
        return null;
    }


    // Получение всех задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    //Получение всех эпиков
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    //Получение всех подзадач
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Получение всех подзадач определённого эпика
    public List<Subtask> getSubtasksForEpic(Integer epicId) {
        List<Subtask> result = new ArrayList<>();

        List<Integer> subtaskIds = epicSubtasks.get(epicId);
        if (subtaskIds != null) {
            for (Integer subtaskId : subtaskIds) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    result.add(subtask);
                }
            }
        }
        return result;
    }

    // Обновление статуса эпика
    private void updateEpicStatus(Integer epicId) {
        List<Subtask> epicSubtaskList = getSubtasksForEpic(epicId);

        if (epicSubtaskList.isEmpty()) {
            epics.get(epicId).setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : epicSubtaskList) {
            Status status = subtask.getStatus();

            if (status != Status.NEW) {
                allNew = false;
            }
            if (status != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epics.get(epicId).setStatus(Status.NEW);
        } else if (allDone) {
            epics.get(epicId).setStatus(Status.DONE);
        } else {
            epics.get(epicId).setStatus(Status.IN_PROGRESS);
        }
    }

    // Удаление задачи по ID
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        }
    }

    // Удаление эпика и подзадач по ID
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            List<Integer> subtaskIds = epicSubtasks.remove(id);
            if (subtaskIds != null) {
                for (int subtaskId : subtaskIds) {
                    subtasks.remove(subtaskId);
                }
            }
        }
    }

    // Удаление всех задач
    public void clearTask() {
        tasks.clear();
    }

    // Удаление всех эпиков
    public void clearEpic() {
        epics.clear();
    }

    // Удаление всех подзадач
    public void clearSubtask() {
        subtasks.clear();
    }
}