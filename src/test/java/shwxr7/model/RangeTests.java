package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangeTests {

  @Test
  @DisplayName("A range cannot be of length zero")
  public void a_range_cannot_be_of_length_zero() {
    assertTrue(Range.of(0).isEmpty());
  }

  @Test
  @DisplayName("A range of length one ends where it starts")
  public void a_range_of_length_one_ends_where_it_starts() {
    var range = Range.of(1).get();

    assertEquals(range.start, range.end);
  }

  @Test
  @DisplayName("There could be no ranges of negative length")
  public void there_could_be_no_ranges_of_negative_length() {
    assertTrue(Range.of(-1).isEmpty());
  }

  @Test
  @DisplayName("Ranges are zero based")
  public void ranges_are_zero_based() {
    var range = Range.of(3).get();

    assertEquals(range, new Range(0, 2));
  }

  @Test
  @DisplayName("A range starting and ending at the same value contains that value")
  public void a_range_starting_and_ending_at_the_same_value_contains_that_value() {
    var range = Range.between(6, 6).get();
    assertTrue(range.contains(6));
  }

  @Test
  @DisplayName("A range starting and ending at the same value contains only that value")
  public void range_cannot_start_and_end_at_the_same_value() {
    var range = Range.between(6, 6).get();

    IntStream.range(-100, 100)
        .filter(i -> i != 6)
        .forEach(action -> assertFalse(range.contains(action)));
  }

  @Test
  @DisplayName("Range start can't be grater than the end")
  public void range_start_can_t_be_grater_than_the_end() {
    assertTrue(Range.between(6, 4).isEmpty());
  }

  @Test
  @DisplayName("No range sequences can be created from '0' length ranges")
  public void no_ranges_can_be_created_from_a_0_marker() {
    var range1 = Range.getRanges(List.of(0));
    var range2 = Range.getRanges(List.of(0, 0, 0));

    assertTrue(range1.isEmpty());
    assertTrue(range2.isEmpty());
  }

  @Test
  @DisplayName("Any 0 markers should be ignored")
  public void any_0_markers_should_be_ignored() {
    var range1 = Range.getRanges(List.of(0, 3, 8));
    var range2 = Range.getRanges(List.of(3, 0, 8));
    var range3 = Range.getRanges(List.of(3, 8, 0));

    var range = Range.getRanges(List.of(3, 8));

    assertArrayEquals(range.toArray(), range1.toArray());
    assertArrayEquals(range.toArray(), range2.toArray());
    assertArrayEquals(range.toArray(), range3.toArray());
  }

  // Get ranges should return an empty list if the list contains negative numbers

  @Test
  @DisplayName("Consecutive ranges have no gaps")
  public void consecutive_ranges_have_no_gaps() {
    var ranges = Range.getRanges(List.of(3, 8, 5));
    var range0_2 = Range.between(0, 2).get();
    var range3_10 = Range.between(3, 10).get();
    var range11_15 = Range.between(11, 15).get();

    assertTrue(ranges.contains(range0_2));
    assertTrue(ranges.contains(range3_10));
    assertTrue(ranges.contains(range11_15));
  }

  @Test
  @DisplayName("Ranges are inclusive on both ends")
  public void ranges_are_inclusive_on_both_ends() {
    var range = Range.between(3, 8).get();

    assertTrue(range.contains(3));
    assertTrue(range.contains(8));
  }

  @Test
  @DisplayName("Ranges do not contain values lesser than their start")
  public void ranges_do_not_contain_values_lesser_than_their_start() {
    var range = Range.between(3, 8).get();

    assertFalse(range.contains(2));
  }

  @Test
  @DisplayName("Ranges do not contain values greater than their end")
  public void ranges_do_not_contain_values_greater_than_their_end() {
    var range = Range.between(3, 8).get();

    assertFalse(range.contains(9));
  }
}
