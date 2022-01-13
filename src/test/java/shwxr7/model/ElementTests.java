package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElementTests {
  @Test
  @DisplayName("The name of an element cannot be null")
  public void the_name_of_an_element_cannot_be_null() {
    var badElement = Element.of(null);

    assertTrue(badElement.isEmpty());
  }

  @Test
  @DisplayName("The name of the element cannot be empty")
  public void the_name_of_the_element_cannot_be_empty() {
    var badElement = Element.of("");

    assertTrue(badElement.isEmpty());
  }

  @Test
  @DisplayName("One shuold be able to create an element with valid name and id")
  public void one_should_be_able_to_create_an_element_with_valid_name_and_id() {
    var element = Element.of("Hydrogen");

    assertTrue(element.isPresent());
    assertEquals(element.get().name, "Hydrogen");
  }

  @Test
  @DisplayName("Elements with the same name are not equal, if created separately")
  public void elements_with_the_same_name_are_not_equal_if_created_separately() {
    var H1 = Element.of("Hydrogen").get();
    var H2 = Element.of("Hydrogen").get();

    assertNotEquals(H1, H2);
  }
}
