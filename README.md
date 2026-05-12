# Task Tracker CLI Application

A simple command-line application to add, update, delete, and track tasks stored in a JSON file.

## Project Link

This project is based on the Task Tracker CLI project from roadmap.sh:  
https://roadmap.sh/projects/task-tracker

## Requirements

- Programming language: Java
- Version: 17 or later
- Tools: `javac` and `java` available on your system PATH

## Installation / Setup

1. Clone or download this project.
2. Compile the code from the project directory, for example:

   ```bash
   javac Main.java Task.java
   ```

3. The application stores tasks in a file named `tasks.json` in the current directory.  
   If `tasks.json` does not exist, it will be created automatically on first write.

## Usage

Run the commands from the directory where you compiled the code.

### Add a task

- Command:

  ```bash
  java Main add "TASK_DESCRIPTION"
  ```

- Example:

  ```bash
  java Main add "Buy groceries"
  ```

### Update a task description

- Command:

  ```bash
  java Main update TASK_ID "NEW_TASK_DESCRIPTION"
  ```

- Example:

  ```bash
  java Main update 1 "Buy groceries and cook dinner"
  ```

### Delete a task

- Command:

  ```bash
  java Main delete TASK_ID
  ```

- Example:

  ```bash
  java Main delete 1
  ```

### Mark a task as in progress

- Command:

  ```bash
  java Main mark-in-progress TASK_ID
  ```

- Example:

  ```bash
  java Main mark-in-progress 1
  ```

### Mark a task as done

- Command:

  ```bash
  java Main mark-done TASK_ID
  ```

- Example:

  ```bash
  java Main mark-done 1
  ```

### List all tasks

- Command:

  ```bash
  java Main list
  ```

### List tasks by status

- Command:

  ```bash
  java Main list STATUS
  ```

- Examples:

  ```bash
  java Main list todo
  java Main list in-progress
  java Main list done
  ```

## Task format and storage

- Each task has the following properties:
    - `id`: unique numeric identifier
    - `description`: short description of the task
    - `status`: `todo`, `in-progress`, or `done`
    - `createdAt`: date and time when the task was created
    - `updatedAt`: date and time when the task was last updated
- Tasks are stored as a JSON array of objects in `tasks.json`.
- The file is read on startup and written after changes.

Example structure of `tasks.json`:

```json
[
  {
    "id": 1,
    "description": "buy groceries",
    "status": "todo",
    "createdAt": "2026-05-09T18:51:17.139954200",
    "updatedAt": "2026-05-09T18:51:17.139954200"
  }
]
```

## Notes and limitations

- This app is intended as a learning project and does not validate the file structure if `tasks.json` is manually edited.
- Task descriptions passed on the command line must be provided as a single argument in quotes, for example `"Buy groceries"`.

## Author

Parth Yadav