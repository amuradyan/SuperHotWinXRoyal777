package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProbabilityTests {
  @Test
  @DisplayName("The probability can't be negative")
  public void probability_cant_be_negative() {
    var optionalProbability = Probability.of(-1);

    assertTrue(optionalProbability.isEmpty());
  }

  @Test
  @DisplayName("The probability can't be greater than 100")
  public void probability_cant_be_greater_than_100() {
    var optionalProbability = Probability.of(150);

    assertTrue(optionalProbability.isEmpty());
  }

  @Test
  @DisplayName("The probability must be between 0 and 100")
  public void probability_must_be_between_zero_and_hundreed() {
    var optionalProbability = Probability.of(50);

    assertTrue(optionalProbability.isPresent());
    assertEquals(optionalProbability.get().probability, 50);
  }

  @Test
  @DisplayName("The probability can be 0")
  public void probability_can_be_zero() {
    var optionalProbability = Probability.of(0);

    assertTrue(optionalProbability.isPresent());
    assertEquals(optionalProbability.get().probability, 0);
  }

  @Test
  @DisplayName("The probability can be 100")
  public void probability_can_be_100() {
    var optionalProbability = Probability.of(100);

    assertTrue(optionalProbability.isPresent());
    assertEquals(optionalProbability.get().probability, 100);
  }
}
