package model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, int epicId, Status status) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                super.toString() +
                ", epicId=" + epicId +
                '}';
    }
}