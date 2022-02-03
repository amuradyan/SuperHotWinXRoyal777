package shwxr7.model;

import java.util.Optional;

public final class Probability {
  public final int probability;

  private Probability(int probability) {
    this.probability = probability;
  }

  public static Optional<Probability> of(int probability) {
    if (probability < 0 || probability > 100) {
      return Optional.empty();
    } else {
      return Optional.of(new Probability(probability));
    }
  }
}
