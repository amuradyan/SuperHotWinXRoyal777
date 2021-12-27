package shwxr7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shwxr7.engine.Engine;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SequenceGeneratorTest {

  @Test
  @DisplayName("When the length of the sequence negative, the output should be an empty when the probability is 0.")
  public void testWhenTheLengthOfTheSequenceNegativeTheOutputShouldBeAnEmptyWhenTheProbabilityIs0() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(0, -1);
    assertTrue(sequence.isEmpty());
  }

  @Test
  @DisplayName("When the length of the sequence is 0, the output should be an empty when the probability is 0.")
  public void testWhenLengthIs0AndProbabilityIs0() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(0, 0);
    assertTrue(sequence.isEmpty());
  }

  @Test
  @DisplayName("When the length of the sequence is 0, the output should be an empty when the probability is 1.")
  public void testWhenLengthIs0AndProbabilityIs1() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(1, 0);
    assertTrue(sequence.isEmpty());
  }

  @Test
  @DisplayName("When the length of the sequence is 0, the output should be an empty when the probability is -1.")
  public void testWhenLengthIs0AndProbabilityIsMinus1() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(-1, 0);
    assertTrue(sequence.isEmpty());
  }

  @Test
  @DisplayName("When the probability is higher than 100, the method should behave as if the probability is 100.")
  public void testWhenProbabilityIsHigherThan100() {
    var sequenceOf100 = Engine.generateASequenceOfOnesWithProbability(100, 10);
    var sequenceOf101 = Engine.generateASequenceOfOnesWithProbability(101, 10);

    assertArrayEquals(sequenceOf100.toArray(), sequenceOf101.toArray());
  }

  @Test
  @DisplayName("When the probability is negative, the method should behave as if the probability is 0.")
  public void testWhenProbabilityIsNegative() {
    var sequenceOf0 = Engine.generateASequenceOfOnesWithProbability(0, 10);
    var sequenceOfMinus1 = Engine.generateASequenceOfOnesWithProbability(-1, 10);

    assertArrayEquals(sequenceOf0.toArray(), sequenceOfMinus1.toArray());
  }

  @Test
  @DisplayName("When the probability is 0, the sequence should contain only 0-s.")
  public void testWhenProbabilityIs0() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(0, 10);
    var expected = new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    assertArrayEquals(expected, sequence.toArray());
  }

  @Test
  @DisplayName("When the probability is 100, the sequence should contain only 1-s.")
  public void testWhenProbabilityIs100() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(100, 10);
    var expected = new Integer[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

    assertArrayEquals(expected, sequence.toArray());
  }

  @Test
  @DisplayName("When the probability is 50, the sequence should contain roughly the same amount of 1-s and 0-s.")
  public void testWhenProbabilityIs50() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(50, 10000);

    var numberOf1s = sequence.stream().filter(i -> i == 1).count();
    var numberOf0s = sequence.stream().filter(i -> i == 0).count();

    assertTrue(Math.abs(1 - ((double) numberOf1s / numberOf0s)) < 0.02);
  }

  @Test
  @DisplayName("When the probability is 20, the sequence should contain roughly 4 times more 1-s than 0-s.")
  public void testWhenProbabilityIs20() {
    var sequence = Engine.generateASequenceOfOnesWithProbability(20, 10000);

    var numberOf1s = sequence.stream().filter(i -> i == 1).count();
    var numberOf0s = sequence.stream().filter(i -> i == 0).count();

    assertTrue(Math.abs(4 - ((double) numberOf0s / numberOf1s)) < 0.26);
  }
}
