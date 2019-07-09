package ablecLanguageServer;
public class MarkupContent {
  MarkupKind kind;
  String value;

  public MarkupContent(MarkupKind kind, String value) {
    this.kind = kind;
    this.value = value;
  }
}
