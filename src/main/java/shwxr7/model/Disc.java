package shwxr7.model;

import java.util.HashMap;
import java.util.Map;

public class Disc {
  private final Config config;

  private Disc(Config config) {
    this.config = config;
  }

  public static final class Config {
    private final Map<Element, Probability> elementProbabilities = new HashMap<>();

    public Config() {
    }

    public Config withSegment(Element element, Probability probability) {
      elementProbabilities.put(element, probability);
      return this;
    }

    public Disc build() {
      if (configIsValid()) {
        return new Disc(this);
      } else {
        throw new IllegalArgumentException("Invalid configuration");
      }
    }

    private boolean configIsValid() {
      return false;
    }
  }
}
