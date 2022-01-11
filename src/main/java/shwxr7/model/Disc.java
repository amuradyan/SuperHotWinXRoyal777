package shwxr7.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Disc {
  private final Config config;

  private Disc(Config config) {
    this.config = config;
  }

  public static final class Config {
    private final Map<Element, Optional<Probability>> dirtyElementProbabilities = new HashMap<>();

    public Config() {
    }

    public Config withSegment(Element element, Optional<Probability> probability) {
      dirtyElementProbabilities.put(element, probability);

      return this;
    }

    public Optional<Disc> build() {
      if (configIsValid(dirtyElementProbabilities)) {
        return Optional.of(new Disc(this));
      } else {
        return Optional.empty();
      }
    }

    protected boolean configIsValid(Map<Element, Optional<Probability>> elementProbabilities) {
      return sumOfAllProbabilities(elementProbabilities) == 100 && !containsBadProbabilities(elementProbabilities);
    }

    protected boolean containsBadProbabilities(Map<Element, Optional<Probability>> elementProbabilities) {
      return elementProbabilities.values().contains(Optional.empty());
    }

    protected Integer sumOfAllProbabilities(Map<Element, Optional<Probability>> elementProbabilities) {
      var sumOfProbabilities = elementProbabilities.values().stream()
          .map(p -> p.isPresent() ? p.get().probability : 0)
          .reduce(0, (a, b) -> a + b);

      return sumOfProbabilities;
    }
  }
}
