package fr.uge.legoandbeer;

import java.util.Objects;

public record Lego(int quantity, String name) implements Article{

  public Lego {
    if (quantity <= 0) {
      throw new IllegalArgumentException("zero or negative quantity");
    }
    Objects.requireNonNull(name);
  }

  @Override
  public Article parseArticle(String line) {
    Objects.requireNonNull(line);
    var fields = line.split(",", -1);
    if (fields.length != 3) {
      throw new IllegalArgumentException("");
    }
    return null;
  }
}
