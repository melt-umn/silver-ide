package ablecLanguageServer;

public class TextDocumentSyncKind implements LSPObject {
  private int kind;

  public static final int NONE = 0;
  public static final int FULL = 1;
  public static final int INCREMENTAL = 2;

  private static final int MIN_VALUE = 0;
  private static final int MAX_VALUE = 2;

  public TextDocumentSyncKind(int kind) {
    this.kind = kind; 
    if (!isValid()) {
      throw new IllegalArgumentException("Invalid value for TextDocumentSyncKind provided");
    }
  }

  public int getKind() {
    return kind;
  }

  public boolean isValid() {
    return kind >= MIN_VALUE && kind <= MAX_VALUE;
  }

  public String getJson() {
    return Integer.toString(kind);
  }
}
