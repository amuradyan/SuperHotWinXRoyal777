package shwxr7.engine;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class WeightedCoin {
  private WeightedCoin() {
  }

  public static Optional<List<Integer>> flipACoinNTimes(int probability, int times) {
    if (probability < 0 || probability > 100 || times < 0) {
      return Optional.empty();
    } else {
      var sequence = IntStream.range(0, times)
          .map(i -> flipACoin(probability).get())
          .boxed()
          .collect(Collectors.toList());

      return Optional.of(sequence);
    }
  }

  public static Optional<Integer> flipACoin(int probability) {
    if (probability < 0 || probability > 100) {
      return Optional.empty();
    } else {
      var value = new Random().nextInt(100) < probability ? 1 : 0;

      return Optional.of(value);
    }
  }
}
