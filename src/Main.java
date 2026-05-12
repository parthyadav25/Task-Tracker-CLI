import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        //load the existing tasks from the json file to our in memory tasks list
        loadTasksFromFile();

        //if no command is given
        if(args.length == 0){
            System.out.println("Enter a command: ");
            return;
        }

        String command = args[0];

        //the following block checks for the type of command entered
        if(command.equals("add"))
            handleAddTask(args);
        else if(command.equals("list"))
            handleListTasks(args);

        else if(command.equals("update"))
            handleUpdateDescription(args);

        else if(command.equals("delete"))
            handleDeleteTask(args);

        else if(command.equals("mark-done"))
            handleMarkTaskDone(args);

        else if(command.equals("mark-in-progress"))
            handleMarkTaskInProgress(args);

        //invalid command case
        else System.out.println("Enter a valid command: ");
    }

    //this method handles adding a task

    public static void handleAddTask(String[] args) {
        //add command needs 2 arguments: add, description
        if(args.length < 2){
            System.out.println("Invalid command: description missing");
            return;
        }

        //evaluate new index for the task to be added
        int maxIndex = 0;
        for(Task task : tasks){
            if(task.getId() > maxIndex){
                maxIndex = task.getId();
            }
        }

        Task newTask = new Task(maxIndex + 1, args[1]);
        tasks.add(newTask);


        System.out.println("Task added successfully (ID: " + newTask.getId() + ")");

        saveTasksToFile();
    }
    //this method handles listing of tasks from the tasks list

    public static void handleListTasks(String[] args){

        //for list action, no more than 2 arguments allowed
        if(args.length > 2){
            System.out.println("Invalid command");
            return;
        }

        //create list to store the tasks to be listed
        List <Task> taskList = new ArrayList<>();

        if(args.length == 1){
            taskList = tasks;
        }

        //tasks by status
        else if(args.length == 2){
            if(!(args[1].equals("todo") || args[1].equals("done") || args[1].equals("in-progress"))){
                System.out.println("Invalid status");
                return;
            }
            taskList = findTasksByStatus(args[1]);
        }

        if(taskList.isEmpty()){
            System.out.println("No tasks found");
            return;
        }

        for(Task task : taskList){
            System.out.println("id: " + task.getId());
            System.out.println("description: " + task.getDescription());
            System.out.println("status: " + task.getStatus());
        }
    }

    private static List<Task> findTasksByStatus(String status) {
        List<Task> taskList = new ArrayList<>();
        for(Task task : tasks){
            if(task.getStatus().equals(status))
                taskList.add(task);
        }
        return taskList;
    }

    //this method handles updating a task description
    public static void handleUpdateDescription(String[] args) {

        //for update, 3 arguments are needed: update, taskid, new description
        if(args.length < 3){
            System.out.println("Invalid command: Task id or new description missing");
            return;
        }
        //assuming user provides a valid int in args[1].
        Integer taskId = parseIntOrNull(args[1]);

        if(taskId == null){
            System.out.println("Invalid task id: Integer id value required");
            return;
        }
        String newTaskDescription = args[2];

        Task task = findTaskById(taskId);

        if(task == null){
            System.out.println("No task found with id: " + taskId);
            return;
        }

        //update task fields if found
        task.setDescription(newTaskDescription);
        task.setUpdatedAt();
        System.out.println("Task with id: " + taskId + " updated.");

        //update the JSON file with updated tasks list
        saveTasksToFile();

    }

    public static void handleDeleteTask(String[] args) {
        //delete command needs 2 arguments: delete, taskid
        if(args.length < 2){
            System.out.println("Invalid command: id missing");
            return;
        }

        Integer taskId = parseIntOrNull(args[1]);

        if(taskId == null){
            System.out.println("Invalid task id: Integer id value required");
            return;
        }
        Task task = findTaskById(taskId);

        if(task == null){
            System.out.println("No task found with id: " + taskId);
            return;
        }

        //if task found, remove it from tasks list
        tasks.remove(task);
        System.out.println("Task with id: " + taskId + " deleted.");

        //update the json file
        saveTasksToFile();
    }

    //this method handles marking a task as done
    private static void handleMarkTaskDone(String[] args) {
        if(args.length < 2){
            System.out.println("Invalid command: id missing");
            return;
        }
        Integer taskId = parseIntOrNull(args[1]);

        if(taskId == null){
            System.out.println("Invalid task id: Integer id value required");
            return;
        }

        Task task = findTaskById(taskId);

        if(task == null){
            System.out.println("No task found with id: " + taskId);
            return;
        }

        if(task.getStatus().equals("done")){
            System.out.println("Task with id: " + taskId + " marked done.");
            return;
        }
        task.setStatus("done");
        task.setUpdatedAt();
        System.out.println("Task with id: " + taskId + " marked done.");

        saveTasksToFile();
    }

    //this method handles marking a task as in-progress
    private static void handleMarkTaskInProgress(String[] args){
        if(args.length < 2){
            System.out.println("Invalid command: id missing");
            return;
        }

        Integer taskId = parseIntOrNull(args[1]);

        if(taskId == null){
            System.out.println("Invalid task id: Integer id value required");
            return;
        }

        Task task = findTaskById(taskId);

        if(task == null){
            System.out.println("No task found with id: " + taskId);
            return;
        }

        if(task.getStatus().equals("in-progress")){
            System.out.println("Task with id: " + taskId + " marked in-progress.");
            return;
        }

        task.setStatus("in-progress");
        task.setUpdatedAt();
        System.out.println("Task with id: " + taskId + " marked in-progress.");

        saveTasksToFile();
    }

    //this method saves all tasks from the tasks list to the JSON file
    public static void saveTasksToFile(){
        File file = new File("tasks.json");

        try(PrintWriter writer = new PrintWriter(file)){
            writer.println("[");
            for(int i = 0; i < tasks.size(); i++){
                Task task = tasks.get(i);
                writer.println("    {");
                writer.println("        \"id\": " + task.getId() + ",");
                writer.println("        \"description\": " + "\"" + task.getDescription() + "\",");
                writer.println("        \"status\": " + "\"" + task.getStatus() + "\",");
                writer.println("        \"createdAt\": " + "\"" + task.getCreatedAt() + "\",");
                writer.println("        \"updatedAt\": " + "\"" + task.getUpdatedAt() + "\"");
                if(i == tasks.size()-1)
                    writer.println("    }");
                else
                    writer.println("    },");
            }
            writer.println("]");
        }catch(Exception e){
            System.out.println("Error writing to file");
        }
    }

    //this method loads the tasks from the JSON file and adds it to tasks list
    public static void loadTasksFromFile() {
        File file = new File("tasks.json");
        if(!file.exists()){
            System.out.println("File does not exist");
            return;
        }

        try(Scanner scanner = new Scanner(file)){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                //{ symbol means beginning of a task object
                if(line.equals("{")){
                    String idLine = scanner.nextLine().trim();
                    int idValue = getTaskId(idLine);

                    String descriptionLine = scanner.nextLine().trim();
                    String description = getTaskDescription(descriptionLine);

                    String statusLine = scanner.nextLine().trim();
                    String status = getTaskStatus(statusLine);

                    String createdAtLine = scanner.nextLine().trim();
                    String createdAt = getTaskCreatedAt(createdAtLine);

                    String updatedAtLine = scanner.nextLine().trim();
                    String updatedAt = getTaskUpdatedAt(updatedAtLine);

                    //now that we have the values of the fields, we can create an object and add to tasks list
                    Task task = new Task(idValue, description, status, createdAt, updatedAt);
                    tasks.add(task);


                }
            }
        } catch (Exception e){
            System.out.println("Error loading tasks from file");
        }

    }

    //helper method to get the id value
    public static int getTaskId(String idLine) {
        String id = idLine.substring(6, idLine.length() - 1); //eg: "id": 12,
        int idValue = Integer.parseInt(id);
        return idValue;
    }
    //helper method to get the description value
    public static String getTaskDescription(String descriptionLine) {
        String description = descriptionLine.substring(16, descriptionLine.length() - 2); //eg: "description": "abcd",
        return description;
    }
    //helper method to get the status value
    public static String getTaskStatus(String statusLine) {
        String status = statusLine.substring(11, statusLine.length() - 2); //eg: "status": "done",
        return status;
    }

    //helper method to get the createdAt value
    public static String getTaskCreatedAt(String createdAtLine) {
        String createdAt = createdAtLine.substring(14, createdAtLine.length() - 2); //eg: "createdAt": "now",
        return createdAt;
    }

    //helper method to get the updatedAt value
    public static String getTaskUpdatedAt(String updatedAtLine) {
        String updatedAt = updatedAtLine.substring(14, updatedAtLine.length() - 1); //eg: "updatedAt": "now"
        return updatedAt;
    }

    //helper method to get a task from task id
    private static Task findTaskById(int taskId) {
        for(int i = 0; i < tasks.size(); i++){
            if(tasks.get(i).getId() == taskId)
                return tasks.get(i);
        }
        return null;
    }

    //helper method to parse int from string
    public static Integer parseIntOrNull(String input){
        try{
            return Integer.parseInt(input);
        } catch(NumberFormatException e){
            return null;
        }
    }
}