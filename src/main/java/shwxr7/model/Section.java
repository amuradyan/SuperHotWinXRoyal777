package shwxr7.model;

import java.util.Optional;

public class Section {
  public final Element element;

  private Section(Element element) {
    this.element = element;
  }

  public static Optional<Section> with(Element e) {
    if (e == null) {
      return Optional.empty();
    } else {
      return Optional.of(new Section(e));
    }
  }
}
