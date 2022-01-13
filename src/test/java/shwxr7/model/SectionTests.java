package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SectionTests {
  @Test
  @DisplayName("Section cannot be created with null element")
  public void section_cannot_be_created_with_null_element() {
    var badSection = Section.with(null);

    assertTrue(badSection.isEmpty());
  }

  @Test
  @DisplayName("Section can be created with valid element")
  public void section_can_be_created_with_valid_element() {
    var He = Element.of("Helium").get();
    var section = Section.with(He);

    assertTrue(section.isPresent());
    assertEquals(section.get().element, He);
  }
}
