import java.time.LocalDateTime;

public class Task {

    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    //constructor
    public Task(int id, String description){
        this.id = id;
        this.description = description;
        status = "todo";
        createdAt = getCurrentTimeStamp();
        updatedAt = createdAt;
    }

    //constructor
    public Task(int id, String description, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdatedAt() {
        updatedAt = getCurrentTimeStamp();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {

        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    //method to get timestamp
    public String getCurrentTimeStamp(){
        LocalDateTime now = LocalDateTime.now();
        return now.toString();
    }
}
