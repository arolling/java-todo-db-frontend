import java.time.LocalDateTime;
import java.util.ArrayList;
import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Task {
  private int id;
  private int categoryId;
  private String description;
  private java.sql.Timestamp dueDate;

  public int getId() {
    return id;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public String getDescription() {
    return description;
  }

  public Timestamp getDueDate() {
    return dueDate;
  }

  public Task(String description, int categoryId, String time) {
    time = time.replace("T", " ");
    time += ":00";
    dueDate = Timestamp.valueOf(time);
    this.description = description;
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
              this.getId() == newTask.getId() &&
              this.getDueDate().equals(newTask.getDueDate()) &&
              this.getCategoryId() == newTask.getCategoryId();

    }
  }

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryId, duedate FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description, categoryId, duedate) VALUES (:description, :categoryId, :duedate)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("categoryId", this.categoryId)
        .addParameter("duedate", this.dueDate)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Tasks where id=:id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static List<Task> getPriorityTasks() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks ORDER BY duedate LIMIT 5";
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public String getCategoryName() {
    Category foundCategory = Category.find(categoryId);
    return foundCategory.getName();
  }
}
