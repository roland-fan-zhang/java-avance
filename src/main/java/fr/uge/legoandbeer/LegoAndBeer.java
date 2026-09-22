package fr.uge.legoandbeer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class LegoAndBeer {

  private LegoAndBeer() {}

  public static List<Article> readArticlesFromFile(Path path) throws IOException {
    Objects.requireNonNull(path);
    try (var lines = Files.lines(path)) {
      return lines
          .filter(s -> !s.isBlank())
          .map(Article::parseArticle).toList();
    }
  }
}
