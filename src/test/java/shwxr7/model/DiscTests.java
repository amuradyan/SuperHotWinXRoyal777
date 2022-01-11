package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscTests {
  @Test
  @DisplayName("There should not be an empty disc")
  public void there_should_not_be_an_empty_disc() {
    var c = new Disc.Config()
        .build();

    assertTrue(c.isEmpty());
  }

  @Test
  @DisplayName("We should be able to create a disc with valid configs")
  public void we_should_be_able_to_create_a_disc_with_valid_configs() {
    var c = new Disc.Config()
        .withSection(new Element("Q", 1), Probability.of(60))
        .withSection(new Element("K", 1), Probability.of(40))
        .build();

    assertTrue(c.isPresent());
  }

  @Test
  @DisplayName("We should be enable tio create a disc with invalid configs")
  public void we_should_be_enable_tio_create_a_disc_with_invalid_configs() {
    var c = new Disc.Config()
        .withSection(new Element("Q", 1), Probability.of(40))
        .withSection(new Element("K", 2), Probability.of(50))
        .build();

    assertTrue(c.isEmpty());
  }
}
