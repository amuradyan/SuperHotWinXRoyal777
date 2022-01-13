package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscConfigTests {

  private final Element _10 = Element.of("10").get();
  private final Element J = Element.of("J").get();
  private final Element Q = Element.of("Q").get();
  private final Element K = Element.of("K").get();
  private final Element A = Element.of("A").get();

  @Test
  @DisplayName("Config should be invalid when the sum of all probabilities is less 100")
  public void config_should_be_valid_when_the_sum_of_all_probabilities_is_not_100() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(40));
    configMap.put(A, Probability.of(10));

    var isConfigValid = new Disc.Config().configIsValid(configMap);

    assertFalse(isConfigValid);
  }

  @Test
  @DisplayName("Config should be invalid when it contains negative probabilities")
  public void sum_of_probabilities_of_all_sections_cant_be_greater_than_100() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(-60));
    configMap.put(K, Probability.of(80));

    var isConfigValid = new Disc.Config().configIsValid(configMap);

    assertFalse(isConfigValid);
  }

  @Test
  @DisplayName("Config should be invalid when it contains over 100% probabilities")
  public void config_should_be_invalid_when_it_contains_over_100_probabilities() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(150));

    var isConfigValid = new Disc.Config().configIsValid(configMap);

    assertFalse(isConfigValid);
  }

  @Test
  @DisplayName("Config should be invalid when sum of probabilities is greater than 100")
  public void config_should_be_invalid_when_sum_of_probabilities_is_greater_than_100() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(70));

    var isConfigValid = new Disc.Config().configIsValid(configMap);

    assertFalse(isConfigValid);
  }

  @Test
  @DisplayName("Config should be invalid when sum of probabilities is less than 0")
  public void config_should_be_invalid_when_sum_of_probabilities_is_less_than_0() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    IntStream.range(0, Integer.MAX_VALUE / 100 + 1)
        .forEach(i -> configMap.put(Element.of("Q").get(), Probability.of(100)));

    var isConfigValid = new Disc.Config().configIsValid(configMap);

    assertFalse(isConfigValid);
  }

  @Test
  @DisplayName("Config should be valid when the sum of all probabilities is 100")
  public void config_should_be_valid_when_the_sum_of_all_probabilities_is_100() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(40));
    configMap.put(A, Probability.of(20));

    var isConfigValid = new Disc.Config().configIsValid(configMap);

    assertTrue(isConfigValid);
  }

  @Test
  @DisplayName("Negative probabilities should not contribute to the sum")
  public void negative_probabilities_should_not_contribute_to_the_sum() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(-30));
    configMap.put(K, Probability.of(30));
    configMap.put(J, Probability.of(50));
    configMap.put(A, Probability.of(-130));
    configMap.put(_10, Probability.of(20));

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 100);
  }

  @Test
  @DisplayName("Over 100% probabilities should not contribute to the sum")
  public void over_100_probabilities_should_not_contribute_to_the_sum() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(150));
    configMap.put(K, Probability.of(30));
    configMap.put(J, Probability.of(50));
    configMap.put(A, Probability.of(130));
    configMap.put(_10, Probability.of(20));

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 100);
  }

  @Test
  @DisplayName("Sum of only bad probabilities should be 0")
  public void sum_of_only_bad_probabilities_should_be_0() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(-30));
    configMap.put(K, Probability.of(-80));
    configMap.put(K, Probability.of(180));

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 0);
  }

  @Test
  @DisplayName("Sum of two 30% precents should be 60%")
  public void sum_of_two_30_percent_precents_should_be_60_percent() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    configMap.put(Q, Probability.of(30));
    configMap.put(K, Probability.of(30));

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 60);
  }

  @Test
  @DisplayName("Sum of no probabilities should be 0")
  public void sum_of_empty_should_be_0() {
    var configMap = new HashMap<Element, Optional<Probability>>();

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 0);
  }
}
