import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId = new ArrayList<>();
    private final HashMap<Integer, Subtask> subtaskInEpic = new HashMap<>();

    private Epic() {
        super();
        this.statusTask = Status.NEW;
    }

    public Epic(String nameTask, String descriptionTask) {
        super(nameTask, descriptionTask);
        this.statusTask = Status.NEW;

    }

    public Epic(int idTask, String nameTask, String descriptionTask) {
        super(idTask, nameTask, descriptionTask);
        this.statusTask = Status.NEW;
    }

    public void addSubtask(Subtask subtask) {
        subtasksId.add(subtask.getIdTask());
        subtaskInEpic.put(subtask.getIdTask(), subtask);
    }

    public void removeSubtask(int subtaskId) {
        subtasksId.remove(Integer.valueOf(subtaskId));
        subtaskInEpic.remove(subtaskId);
    }

    public HashMap<Integer,Subtask> getSubtasksId() {
        return new HashMap<>(subtaskInEpic);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksId, epic.subtasksId) &&
                Objects.equals(subtaskInEpic, epic.subtaskInEpic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksId, subtaskInEpic);
    }

    @Override
    public String toString() {
        return idTask + ","
                + TypeTask.EPIC + ","
                + nameTask + ","
                + statusTask + ","
                + descriptionTask;
    }
}