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
    private final Map<Integer, List<Integer>> epicSubtasks = new HashMap<>();
    private int nextId = 1;

    private synchronized int generateId() {
        return nextId++;
    }

    private void checkForDuplicateId(int id, Map<Integer, ? > map, String entityType) {
        if (map.containsKey(id)) {
            throw new IllegalArgumentException(entityType + " with ID " + id + " already exists.");
        }
    }

    // Добавление новой задачи
    public Task addTask(Task task) {
        int id = generateId();
        task = new Task(id, task.getName(), task.getDescription(), task.getStatus());
        checkForDuplicateId(id, this.tasks, "Task");
        this.tasks.put(id, task);
        return task;
    }

    // Добавление нового эпика
    public Epic addEpic(Epic epic) {
        int id = generateId();
        epic = new Epic(id, epic.getName(), epic.getDescription(), epic.getStatus());
        checkForDuplicateId(id, this.epics, "Epic");

        this.epics.put(id, epic);
        this.epicSubtasks.put(id, new ArrayList<>());
        return epic;
    }

    // Добавление новой подзадачи
    public Subtask addSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            throw new IllegalArgumentException("Epic with ID " + epicId + " does not exist.");
        }

        //ID для подзадачи
        int id = generateId();
        checkForDuplicateId(id, this.subtasks, "Subtask");
        Subtask newSubtask = new Subtask(id, subtask.getName(), subtask.getDescription(), epicId, subtask.getStatus());
        this.subtasks.put(id, newSubtask);
        this.epicSubtasks.get(epicId).add(id);
        return newSubtask;
    }

    public Map<Integer, Subtask> getAllSubtasks() {
        return Collections.unmodifiableMap(subtasks);
    }

    // Обновление задачи (Задача, Эпик или Подзадача)
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

    // Удаление задачи по ID
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            epics.remove(id);
            epicSubtasks.remove(id);  // Удаляем подзадачи эпика
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.remove(id);
            epicSubtasks.get(subtask.getEpicId()).remove(Integer.valueOf(id));
            updateEpicStatus(subtask.getEpicId());  // Обновляем статус эпика
        }
    }

    // Удаление всех задач
    public void clearAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
        epicSubtasks.clear();
    }

    // Получение задачи по ID
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) return task;

        Epic epic = epics.get(id);
        if (epic != null) return epic;

        Subtask subtask = subtasks.get(id);
        if (subtask != null) return subtask;

        throw new IllegalArgumentException("Задача с ID " + id + " не найдена.");
    }

    // Получение всех задач
    public Collection<Task> getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.addAll(tasks.values());
        allTasks.addAll(epics.values());
        allTasks.addAll(subtasks.values());
        return allTasks;
    }

    // Получение всех подзадач определённого эпика
    public List<Integer> getSubtasksForEpic(Integer epicId) {
        if (!epicSubtasks.containsKey(epicId)) {
            throw new IllegalArgumentException("Epic with ID " + epicId + " does not exist.");
        }
        return new ArrayList<>(epicSubtasks.get(epicId)); // Возвращаем список ID подзадач
    }

    // Обновление статуса эпика
    public void updateEpicStatus(Integer epicId) {
        List<Integer> subtaskIds = getSubtasksForEpic(epicId);
        boolean allNew = true;
        boolean allDone = true;

        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = getAllSubtasks().get(subtaskId);

            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }

            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        // Обновление статуса эпика в зависимости от статусов подзадач
        Epic epic = epics.get(epicId);
        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}