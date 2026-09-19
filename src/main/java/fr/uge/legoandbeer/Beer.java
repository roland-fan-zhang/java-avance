package fr.uge.legoandbeer;

import java.util.Objects;

public record Beer(int quantity, BeerKind kind) implements Article {

  public Beer {
    if (quantity <= 0) {
      throw new IllegalArgumentException("zero or negative quantity");
    }
    Objects.requireNonNull(kind);
  }
}
