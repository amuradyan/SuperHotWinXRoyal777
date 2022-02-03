package shwxr7.model;

import java.util.Optional;

public final class Element {
  public final String name;

  private Element(String name) {
    this.name = name;
  }

  public static Optional<Element> of(String name) {
    if (name == null || name.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new Element(name));
    }
  }
}
