package fr.uge.legoandbeer;

public sealed interface Article permits Lego, Beer {

  Article parseArticle(String line);
}
