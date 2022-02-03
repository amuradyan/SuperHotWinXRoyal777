package shwxr7.model;

import java.util.Optional;

public final class Section {
  public final Element element;

  private Section(Element element) {
    this.element = element;
  }

  public static Optional<Section> with(Element e) {
    return e == null ? Optional.empty() : Optional.of(new Section(e));
  }
}
