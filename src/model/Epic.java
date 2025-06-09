package model;

import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public void removeSubtask(int subtaskId) {
        subtaskIds.remove(Integer.valueOf(subtaskId));
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                super.toString() +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}