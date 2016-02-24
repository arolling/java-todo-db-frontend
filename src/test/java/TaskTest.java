import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.junit.Rule;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn", 5, "2016-02-24T03:58");
    Task secondTask = new Task("Mow the lawn", 5, "2016-02-24T03:58");
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void equals_returnsTrueIfDueDatesAretheSame() {
    Task firstTask = new Task("Mow the lawn", 5, "2016-02-24T03:58");
    Task secondTask = new Task("Mow the lawn", 5, "2016-02-24T03:58");
    assertEquals(firstTask.getDueDate(), secondTask.getDueDate());
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame() {
    Task myTask = new Task("Mow the Lawn", 5, "2016-02-24T03:58");
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_assisgnsIDToObject() {
    Task myTask = new Task("Mow the lawn", 5, "2016-02-24T03:58");
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", 5, "2016-02-24T03:58");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void save_savesCategoryIdIntDB_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", myCategory.getId(), "2016-02-24T03:58");
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  @Test
  public void update_changesTaskDescription_false() {
    Task myTask = new Task("Feed the chickens", 1, "2016-02-24T03:58");
    myTask.save();
    myTask.update("Feed the cats");
    Task updatedTask = Task.find(myTask.getId());
    assertFalse(updatedTask.equals(myTask));
  }

  @Test
  public void delete_removesTaskFromDatabase_false() {
    Task myTask = new Task("Feed the chickens", 1, "2016-02-24T03:58");
    Task secondTask = new Task("Mow the lawn", 1, "2016-02-24T03:58");
    myTask.save();
    secondTask.save();
    myTask.delete();
    assertFalse(Task.all().contains(myTask));
  }
}
