package shwxr7.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Disc {
  private final Map<Element, Range> sectionRanges = new HashMap<>();

  private Disc(Map<Element, Range> sectionRanges) {
    if (sectionRanges != null) {
      this.sectionRanges.putAll(sectionRanges);
    }
  }

  public Section roll() {
    var value = new Random().nextInt(100);

    var section = sectionRanges.entrySet().stream()
        .filter(e -> {
          return value >= e.getValue().start && value <= e.getValue().end;
        })
        .findAny()
        .get();

    return Section.with(section.getKey()).get();
  }

  public static final class Config {
    private final Map<Element, Optional<Probability>> dirtyElementProbabilities = new HashMap<>();

    public Config withSection(Element element, Optional<Probability> probability) {
      dirtyElementProbabilities.put(element, probability);

      return this;
    }

    public Optional<Disc> build() {
      if (configIsValid(dirtyElementProbabilities)) {
        var sectionRanges = toSectionRanges(dirtyElementProbabilities);

        return Optional.of(new Disc(sectionRanges));
      } else {
        return Optional.empty();
      }
    }

    Map<Element, Range> toSectionRanges(Map<Element, Optional<Probability>> dirtyElementProbabilities) {
      var sectionRanges = new HashMap<Element, Range>();

      if (dirtyElementProbabilities != null) {
        var acc = 0;

        for (var dep : dirtyElementProbabilities.entrySet()) {
          var probability = dep.getValue().get().probability;

          if (probability > 0) {
            var range = Range.of(probability).get();

            sectionRanges.put(dep.getKey(), range.shift(acc));
            acc += probability;
          }
        }
      }

      return sectionRanges;
    }

    boolean configIsValid(Map<Element, Optional<Probability>> elementProbabilities) {
      return sumOfAllProbabilities(elementProbabilities) == 100 && !containsBadProbabilities(elementProbabilities);
    }

    boolean containsBadProbabilities(Map<Element, Optional<Probability>> elementProbabilities) {
      return elementProbabilities.values().contains(Optional.empty());
    }

    Integer sumOfAllProbabilities(Map<Element, Optional<Probability>> elementProbabilities) {
      return elementProbabilities.values().stream()
          .map(p -> p.isPresent() ? p.get().probability : 0)
          .reduce(0, (a, b) -> a + b);
    }
  }
}
