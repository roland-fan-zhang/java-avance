package fr.uge.legoandbeer;

import java.util.Objects;

public record Lego(int quantity, String name) implements Article {

  public Lego {
    if (quantity <= 0) {
      throw new IllegalArgumentException("zero or negative quantity");
    }
    Objects.requireNonNull(name);
  }
}
