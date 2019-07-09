package ablecLanguageServer;

import org.json.*;

public class MarkupKind {
  private String kind;
  private static final String ILLEGAL_VALUE_ERROR_MSG_BASE =
    "MarkupKind string must be 'plaintext' or 'markup' instead it was: ";
  public static final String PLAINTEXT = "plaintext";
  public static final String MARKDOWN = "markdown";

  public MarkupKind(String kind) {
    if (!kind.equals(MarkupKind.PLAINTEXT) && !kind.equals(MarkupKind.MARKDOWN)) {
      throw new IllegalArgumentException(ILLEGAL_VALUE_ERROR_MSG_BASE + kind);
    }
    this.kind = kind;
  }

  public String getKind() {
    return kind;
  }

  public static MarkupKind[] parseMarkupKindJSONArray(JSONArray ops) {
    int entries = ops.length();
    MarkupKind[] kinds = new MarkupKind[entries];
    for (int i = 0; i < entries; i++) {
      kinds[i] = new MarkupKind(ops.getString(i));
    }
    return kinds;
  }
}
