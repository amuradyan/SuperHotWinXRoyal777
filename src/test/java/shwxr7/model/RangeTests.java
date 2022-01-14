package shwxr7.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RangeTests {

  @Test
  @DisplayName("the method should return empty range when it receive an empty range")
  public void the_method_should_return_empty_range_when_it_receive_an_empty_range() {

  }

  // [] -> ()
  // No ranges can be created without markers

  // [0] -> ()
  // [0, 0, 0] -> ()
  // No ranges can be created off of an 0 marker(s)

  // [0, 3, 8] -> [(0, 3), (4, 11)]
  // [3, 0, 8] -> [(0, 3), (4, 11)]
  // [3, 8, 0] -> [(0, 3), (4, 11)]
  // 0 markers should be ignored

  // [3] -> (0, 3)
  // [3, 8] -> [(0, 3), (4, 11)]
  // [3, 3, 4] -> [(0, 3), (4, 7), (8, 12)]
  // [3, 3, 4, 8] -> [(0, 3), (4, 7), (8, 12), (13, 20)]

}

// [A - 1, K - 3, Q - 4]
// [1, 3, 4]
// A - R[0, 1], K - R[2, 5], Q - R[6, 10]
// 7