package shwxr7.engine;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightedCoinFlipTests {

  @Test
  @DisplayName("When the length of the sequence is negative, the output should be an empty optional")
  public void test_negative_length() {
    var optionalSequenceOfOnes = WeightedCoin.flipACoinNTimes(100, -1);
    var optionalSequenceOfZeros = WeightedCoin.flipACoinNTimes(0, -1);

    assertTrue(optionalSequenceOfOnes.isEmpty());
    assertTrue(optionalSequenceOfZeros.isEmpty());
  }

  @Test
  @DisplayName("When the length of the sequence is 0, the output should be an empty sequence, regardless of the probability")
  public void test_zero_length() {
    var optionalSequenceOfOnes = WeightedCoin.flipACoinNTimes(1, 0);
    var optionalSequenceOfZeros = WeightedCoin.flipACoinNTimes(0, 0);

    assertTrue(optionalSequenceOfOnes.isPresent() && optionalSequenceOfOnes.get().isEmpty());
    assertTrue(optionalSequenceOfZeros.isPresent() && optionalSequenceOfZeros.get().isEmpty());
  }

  @Test
  @DisplayName("The output should be an empty optional when the probability is negative, regardless of length.")
  public void test_negative_probability() {
    var optionalSequenceOfZeroLength = WeightedCoin.flipACoinNTimes(-1, 0);
    var optionalSequenceOfNonZeroLength = WeightedCoin.flipACoinNTimes(-1, 1);

    assertTrue(optionalSequenceOfZeroLength.isEmpty());
    assertTrue(optionalSequenceOfNonZeroLength.isEmpty());
  }

  @Test
  @DisplayName("When the probability is higher than 100, the method should return an empty optional.")
  public void test_probability_too_high() {
    var optionalSequence = WeightedCoin.flipACoinNTimes(101, 0);

    assertTrue(optionalSequence.isEmpty());
  }

  @Test
  @DisplayName("When the probability is negative, the method should return an empty optional.")
  public void test_probability_too_low() {
    var optionalSequence = WeightedCoin.flipACoinNTimes(-1, 0);

    assertTrue(optionalSequence.isEmpty());
  }

  @Test
  @DisplayName("When the probability is 0, the sequence should contain only 0-s.")
  public void test_probability_zero() {
    var optionalSequence = WeightedCoin.flipACoinNTimes(0, 10);

    boolean isAllZeros = optionalSequence.isPresent() && optionalSequence.get().stream().allMatch(i -> i == 0);

    assertTrue(isAllZeros);
  }

  @Test
  @DisplayName("When the probability is 100, the sequence should contain only 1-s.")
  public void test_probability_one_hundred() {
    var optionalSequence = WeightedCoin.flipACoinNTimes(100, 10);

    boolean isAllOnes = optionalSequence.isPresent() && optionalSequence.get().stream().allMatch(i -> i == 1);

    assertTrue(isAllOnes);
  }

  @Test
  @DisplayName("When the probability is 50, the sequence should contain roughly the same amount of 1-s and 0-s.")
  public void test_probability_fifty() {
    var optionalSequence = WeightedCoin.flipACoinNTimes(50, 100000);

    var numberOf1s = optionalSequence.get().stream().filter(i -> i == 1).count();
    var numberOf0s = optionalSequence.get().stream().filter(i -> i == 0).count();

    assertTrue(Math.abs(1 - ((double) numberOf1s / numberOf0s)) < 0.02);
  }

  @Test
  @DisplayName("When the probability is 20, the sequence should contain roughly 4 times more 1-s than 0-s.")
  public void testWhenProbabilityIs20() {
    var optionalSequence = WeightedCoin.flipACoinNTimes(20, 100000);

    var numberOf1s = optionalSequence.get().stream().filter(i -> i == 1).count();
    var numberOf0s = optionalSequence.get().stream().filter(i -> i == 0).count();

    assertTrue(Math.abs(4 - ((double) numberOf0s / numberOf1s)) < 0.26);
  }

  @Test
  @DisplayName("When we flip a coin with negative probability, the method should return an empty optional.")
  public void test_when_we_flip_a_coin_with_negative_probability_the_method_should_return_an_empty_optional() {
    var outcome = WeightedCoin.flipACoin(-1);

    assertTrue(outcome.isEmpty());
  }

  @Test
  @DisplayName("When we flip a coin with probability higher than 100, the method should return an empty optional.")
  public void test_when_we_flip_a_coin_with_probability_higher_than_100_the_method_should_return_an_empty_optional() {
    var outcome = WeightedCoin.flipACoin(101);

    assertTrue(outcome.isEmpty());
  }

  @Test
  @DisplayName("When we flip a coin with probability of `1` being 0, the method should return 0.")
  public void test_when_we_flip_a_coin_with_probability_of_1_being_0_the_method_should_return_0() {
    var outcome = WeightedCoin.flipACoin(0);

    assertTrue(outcome.isPresent());
    assertTrue(outcome.get() == 0);
  }

  @Test
  @DisplayName("When we flip a coin with probability of `1` being 100, the method should return 1.")
  public void test_when_we_flip_a_coin_with_probability_of_1_being_100_the_method_should_return_1() {
    var outcome = WeightedCoin.flipACoin(100);

    assertTrue(outcome.isPresent());
    assertTrue(outcome.get() == 1);
  }
}
