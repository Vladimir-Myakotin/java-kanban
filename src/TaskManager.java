import java.util.Objects;

public class TaskManager extends Task {

    private TaskManager() {
        super();
        this.statusTask = Status.NEW;
    }

        public TaskManager(String nameTask, String descriptionTask, Status statusTask) {
            super(nameTask, descriptionTask);
            this.statusTask = statusTask;

        }

        public TaskManager(int idTask, String nameTask, String descriptionTask, Status statusTask) {
            super(idTask, nameTask, descriptionTask);
            this.statusTask = statusTask;
        }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        TaskManager taskManager = (TaskManager) o;
        return statusTask == taskManager.statusTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), statusTask);
    }

    @Override
        public String toString() {
            return idTask + ","
                    + TypeTask.TASK + ","
                    + nameTask + ","
                    + statusTask + ","
                    + descriptionTask;
        }
    }
