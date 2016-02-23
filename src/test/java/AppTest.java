import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Rule;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Task Manager!");
  }

  @Test
  public void createNewCategory() {
    goTo("http://localhost:4567/");
    click("a", withText("Manage Categories"));
    fill("#newCategory").with("Household");
    submit(".btn");
    assertThat(pageSource()).contains("Household");
  }

  @Test
  public void singleCategoryPageIsDisplayed() {
    Category testCategory = new Category("Yardwork");
    testCategory.save();
    String categoryPath = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Yardwork");
  }

  @Test
  public void addTaskFormAddsTask() {
    Category testCategory = new Category("Yardwork");
    testCategory.save();
    String categoryPath = String.format("http://localhost:4567/categories/%d", testCategory.getId());
    goTo(categoryPath);
    fill("#newTask").with("Weeding");
    fill("#taskDueDate").with("2016-02-24T10:00");
    submit(".btn");
    assertThat(pageSource()).contains("Weeding");
  }

  // @Test
  // public void allTasksDisplayDescriptionOnCategoryPage() {
  //   Category myCategory = new Category("Household chores");
  //   myCategory.save();
  //   Task firstTask = new Task("Mow the lawn", myCategory.getId());
  //   firstTask.save()
  //   Task secondTask = new Task("Do the dishes", myCategory.getId());
  //   secondTask.save()
  //   String categoryPath = String.format("http://localhost:4567/categories/%d", myCategory.getId());
  //   goTo(categoryPath);
  //   assertThat(pageSource()).contains("Mow the lawn");
  //   assertThat(pageSource()).contains("Do the dishes");
  // }
}
