package shwxr7.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
    static class DirtyConfig extends HashMap<Element, Optional<Probability>> {
    };

    private final DirtyConfig dirtyElementProbabilities = new DirtyConfig();

    public Config withSection(Element element, Optional<Probability> probability) {
      dirtyElementProbabilities.put(element, probability);

      return this;
    }

    public Optional<Disc> build() {
      return configIsValid(dirtyElementProbabilities)
          .flatMap(validatedConfig -> toSectionRanges(validatedConfig))
          .map(ranges -> new Disc(ranges));
    }

    Optional<Map<Element, Range>> toSectionRanges(DirtyConfig dirtyElementProbabilities) {
      if (dirtyElementProbabilities == null || dirtyElementProbabilities.isEmpty()) {
        return Optional.empty();
      }

      var positiveProbabilities = dirtyElementProbabilities.entrySet().stream()
          .filter(e -> e.getValue().get().probability > 0)
          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

      var sectionRanges = getRanges(positiveProbabilities);

      return Optional.of(sectionRanges);
    }

    private HashMap<Element, Range> getRanges(Map<Element, Optional<Probability>> positiveProbabilities) {
      var sectionRanges = new HashMap<Element, Range>();
      var acc = 0;

      for (var dep : positiveProbabilities.entrySet()) {
        var probability = dep.getValue().get().probability;
        var range = Range.of(probability).get();

        sectionRanges.put(dep.getKey(), range.shift(acc));
        acc += probability;
      }

      return sectionRanges;
    }

    Optional<DirtyConfig> configIsValid(DirtyConfig elementProbabilities) {

      var isConfigValid = sumOfAllProbabilities(elementProbabilities) == 100
          && !containsBadProbabilities(elementProbabilities);

      if (isConfigValid) {
        return Optional.of(elementProbabilities);
      } else {
        return Optional.empty();
      }
    }

    boolean containsBadProbabilities(DirtyConfig elementProbabilities) {
      return elementProbabilities.values().contains(Optional.empty());
    }

    Integer sumOfAllProbabilities(DirtyConfig elementProbabilities) {
      return elementProbabilities.values().stream()
          .map(p -> p.isPresent() ? p.get().probability : 0)
          .reduce(0, (a, b) -> a + b);
    }
  }
}
