package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscTests {
  private final Element Q = Element.of("Q").get();
  private final Element K = Element.of("K").get();
  private final Element J = Element.of("J").get();

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
        .withSection(Q, Probability.of(60))
        .withSection(K, Probability.of(40))
        .build();

    assertTrue(c.isPresent());
  }

  @Test
  @DisplayName("We should be able to create a disc with invalid configs")
  public void we_should_be_able_to_create_a_disc_with_invalid_configs() {
    var c = new Disc.Config()
        .withSection(Q, Probability.of(40))
        .withSection(K, Probability.of(50))
        .build();

    assertTrue(c.isEmpty());
  }

  @Test
  @DisplayName("If a disc contains only one section, the roll should result in that section")
  public void if_a_disc_contains_only_one_section_the_roll_should_result_in_that_section() {
    var disc = new Disc.Config()
        .withSection(K, Probability.of(100))
        .build()
        .get();

    var outcome = disc.roll();

    assertEquals(K, outcome.element);
  }

  @Test
  @DisplayName("If a disc contains more than one section, with one of them having a probability of 100%, the roll should result in that one section")
  public void if_a_disc_contains_more_than_one_section_with_one_of_them_having_a_probability_of_100_the_roll_should_result_in_that_one_section() {
    var disc = new Disc.Config()
        .withSection(K, Probability.of(100))
        .withSection(Q, Probability.of(0))
        .withSection(J, Probability.of(0))
        .build()
        .get();

    var outcome = disc.roll();

    assertEquals(K, outcome.element);
  }

  @Test
  @DisplayName("If a disc contains more than one section, with one of them having a probability of 0%, the roll should not result in that one section")
  public void if_a_disc_contains_more_than_one_section_with_one_of_them_having_a_probability_of_0_the_roll_should_not_result_in_that_one_section() {
    var disc = new Disc.Config()
        .withSection(K, Probability.of(50))
        .withSection(Q, Probability.of(0))
        .withSection(J, Probability.of(50))
        .build()
        .get();

    var outcome = disc.roll();

    assertNotEquals(Q, outcome.element);
  }

  @Test
  @DisplayName("If a disc contains two sections, with one of them having a probability of 0%, the roll should result in the other section")
  public void if_a_disc_contains_two_sections_with_one_of_them_having_a_probability_of_0_the_roll_should_result_in_the_other_section() {
    var disc = new Disc.Config()
        .withSection(K, Probability.of(0))
        .withSection(Q, Probability.of(100))
        .build()
        .get();

    var outcome = disc.roll();

    assertEquals(Q, outcome.element);
  }
}
