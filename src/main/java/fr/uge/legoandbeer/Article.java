package fr.uge.legoandbeer;

import java.util.Locale;

public sealed interface Article permits Lego, Beer {

  static Article parseArticle(String line) {
    var tokens = line.split(",", -1);
    if (tokens.length != 3) {
      throw new IllegalArgumentException("too few or too many tokens");
    }
    return switch (tokens[0]) {
      case "lego" -> new Lego(Integer.parseInt(tokens[1]), tokens[2]);
      case "beer" -> new Beer(Integer.parseInt(tokens[1]), BeerKind.valueOf(tokens[2].toUpperCase(Locale.ROOT)));
      default -> throw new IllegalArgumentException("unrecognized article type");
    };
  }
}
