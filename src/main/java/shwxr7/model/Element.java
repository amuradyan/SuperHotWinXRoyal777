package shwxr7.model;

import java.util.Optional;

public class Element {
  public final String name;
  private final long id;

  private Element(String name) {
    this.name = name;
    this.id = System.currentTimeMillis();
  }

  public static Optional<Element> of(String name) {
    if (name == null || name.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(new Element(name));
    }
  }
}
