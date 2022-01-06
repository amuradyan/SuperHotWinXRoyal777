package shwxr7.model;

public class Probability {
  public final int probability;

  public Probability(int probability) {
    if (probability < 0 || probability > 100) {
      throw new IllegalArgumentException("Probability must be between 0 and 100");
    } else {
      this.probability = probability;
    }
  }
}