package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscConfigTests {

  private final Element _10 = Element.of("10").get();
  private final Element J = Element.of("J").get();
  private final Element Q = Element.of("Q").get();
  private final Element K = Element.of("K").get();
  private final Element A = Element.of("A").get();

  @Test
  @DisplayName("Config should be invalid when the sum of all probabilities is less than 100")
  public void config_should_be_valid_when_the_sum_of_all_probabilities_is_less_than_100() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(40));
    configMap.put(A, Probability.of(10));

    var validatedConfig = new Disc.Config().configIsValid(configMap);

    assertTrue(validatedConfig.isEmpty());
  }

  @Test
  @DisplayName("Config should be invalid when it contains negative probabilities")
  public void sum_of_probabilities_of_all_sections_cant_be_greater_than_100() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(-60));
    configMap.put(K, Probability.of(80));

    var validatedConfig = new Disc.Config().configIsValid(configMap);

    assertTrue(validatedConfig.isEmpty());
  }

  @Test
  @DisplayName("Config should be invalid when it contains over 100% probabilities")
  public void config_should_be_invalid_when_it_contains_over_100_probabilities() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(150));

    var validatedConfig = new Disc.Config().configIsValid(configMap);

    assertTrue(validatedConfig.isEmpty());
  }

  @Test
  @DisplayName("Config should be invalid when sum of probabilities is greater than 100")
  public void config_should_be_invalid_when_sum_of_probabilities_is_greater_than_100() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(70));

    var validatedConfig = new Disc.Config().configIsValid(configMap);

    assertTrue(validatedConfig.isEmpty());
  }

  @Test
  @DisplayName("Config should be invalid when sum of probabilities is less than 0")
  public void config_should_be_invalid_when_sum_of_probabilities_is_less_than_0() {
    var configMap = new Disc.Config.DirtyConfig();

    IntStream.range(0, Integer.MAX_VALUE / 100 + 1)
        .forEach(i -> configMap.put(Element.of("Q").get(), Probability.of(100)));

    var validatedConfig = new Disc.Config().configIsValid(configMap);

    assertTrue(validatedConfig.isEmpty());
  }

  @Test
  @DisplayName("Config should be valid when the sum of all probabilities is 100")
  public void config_should_be_valid_when_the_sum_of_all_probabilities_is_100() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(40));
    configMap.put(K, Probability.of(40));
    configMap.put(A, Probability.of(20));

    var validatedConfig = new Disc.Config().configIsValid(configMap);

    assertTrue(validatedConfig.isPresent());
    assertEquals(configMap, validatedConfig.get());
  }

  @Test
  @DisplayName("Negative probabilities should not contribute to the sum")
  public void negative_probabilities_should_not_contribute_to_the_sum() {
    var configMap = new Disc.Config.DirtyConfig();

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
    var configMap = new Disc.Config.DirtyConfig();

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
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(-30));
    configMap.put(K, Probability.of(-80));
    configMap.put(K, Probability.of(180));

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 0);
  }

  @Test
  @DisplayName("Sum of two 30% percents should be 60%")
  public void sum_of_two_30_percent_percents_should_be_60_percent() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(30));
    configMap.put(K, Probability.of(30));

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 60);
  }

  @Test
  @DisplayName("Sum of no probabilities should be 0")
  public void sum_of_empty_should_be_0() {
    var configMap = new Disc.Config.DirtyConfig();

    assertEquals(new Disc.Config().sumOfAllProbabilities(configMap), 0);
  }

  @Test
  @DisplayName("Missing config can't produce ranges")
  public void missing_config_cant_produce_ranges() {
    var ranges = new Disc.Config().toSectionRanges(null);

    assertTrue(ranges.isEmpty());
  }

  @Test
  @DisplayName("Empty config can't produce section ranges")
  public void empty_config_cant_produce_section_ranges() {
    var ranges = new Disc.Config().toSectionRanges(new Disc.Config.DirtyConfig());

    assertTrue(ranges.isEmpty());
  }

  @Test
  @DisplayName("One element with probability of 100% should result in a range with cardinality of 100")
  public void one_element_with_probability_of_100_percent_should_result_in_a_range_with_cardinality_of_100() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(K, Probability.of(100));

    var ranges = new Disc.Config().toSectionRanges(configMap);

    assertEquals(1, ranges.get().size());
    assertEquals(100, ranges.get().get(K).size());
  }

  @Test
  @DisplayName("Two elements with 50 percent chance, should produce two ranges of length 50")
  public void two_elements_with_50_percent_chance_should_produce_two_ranges_of_length_50() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(50));
    configMap.put(K, Probability.of(50));

    var ranges = new Disc.Config().toSectionRanges(configMap);

    assertEquals(2, ranges.get().size());
    ranges.get().forEach((l, range) -> assertEquals(50, range.size()));
  }

  @Test
  @DisplayName("Section ranges should ignore zero probabilities sections")
  public void section_ranges_should_ignore_zero_probabilities_sections() {
    var configMap = new Disc.Config.DirtyConfig();

    configMap.put(Q, Probability.of(100));
    configMap.put(K, Probability.of(0));
    configMap.put(J, Probability.of(0));

    var ranges = new Disc.Config().toSectionRanges(configMap);

    assertEquals(1, ranges.get().size());
  }
}
