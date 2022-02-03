package shwxr7.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Range {
  public final int start;
  public final int end;

  Range(int start, int end) {
    this.start = start;
    this.end = end;
  }

  int size() {
    return end - start + 1;
  }

  public static Optional<Range> of(int length) {
    return length < 1 ? Optional.empty() : Optional.of(new Range(0, length - 1));
  }

  public static Optional<Range> between(int start, int end) {
    return start > end ? Optional.empty() : Optional.of(new Range(start, end));
  }

  public static List<Range> getRanges(List<Integer> lengths) {
    List<Range> ranges = new ArrayList<Range>();
    var acc = 0;

    if (lengths != null && validateLengths(lengths)) {
      for (Integer length : lengths) {
        if (length != 0) {
          ranges.add(new Range(acc, acc + length - 1));
          acc += length;
        }
      }
    }

    return ranges;
  }

  static Boolean validateLengths(List<Integer> lengths) {
    if (!lengths.isEmpty() &&
        lengths.stream().noneMatch(l -> l == null || l < 0)) {
      return true;
    } else {
      return false;
    }

  }

  public Boolean contains(int value) {
    return start <= value && value <= end;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Range)) {
      return false;
    }

    Range range = (Range) o;

    return start == range.start && end == range.end;
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }

  public Range shift(int shift) {
    return new Range(start + shift, end + shift);
  }
}
