import java.util.Objects;

public class Subtask extends Task {
    private final int epicId;

    private Subtask(int newId, String name, String description) {
        super();
        this.epicId = 0;
    }

    public Subtask(String nameTask, String descriptionTask, Status statusTask, int epicId) {
        super(nameTask, descriptionTask);
        this.statusTask = statusTask;
        this.epicId = epicId;

    }

    public Subtask(int idTask, String nameTask, String descriptionTask, Status statusTask, int epicId) {
        super(idTask, nameTask, descriptionTask);
        this.statusTask = statusTask;
        this.epicId = epicId;

    }

    public Subtask createSubtask(String name, String description) {
        int newId = generateId();
        return new Subtask(newId, name, description);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return idTask + ","
                + TypeTask.SUBTASK + ","
                + nameTask + ","
                + statusTask + ","
                + descriptionTask + ","
                + epicId;
    }
}