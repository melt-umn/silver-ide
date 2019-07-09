package ablecLanguageServer;

import org.json.*;

public class CodeActionKind {
  private String kind;
  private static final String ILLEGAL_VALUE_ERROR_MSG_BASE =
    "CodeActionKind string must be one of\n " +
    CodeActionKind.QUICK_FIX + "\n" +
    CodeActionKind.REFACTOR + "\n" +
    CodeActionKind.REFACTOR_EXTRACT + "\n" +
    CodeActionKind.REFACTOR_INLINE + "\n" +
    CodeActionKind.REFACTOR_REWRITE + "\n" +
    CodeActionKind.SOURCE + "\n" +
    CodeActionKind.SOURCE_ORGANIZE_IMPORTS + "\n" +
    "Instead it was: ";
  public static final String QUICK_FIX = "quickfix";
  public static final String REFACTOR = "refactor";
  public static final String REFACTOR_EXTRACT = "refactor.extract";
  public static final String REFACTOR_INLINE = "refactor.inline";
  public static final String REFACTOR_REWRITE = "refactor.rewrite";
  public static final String SOURCE = "source";
  public static final String SOURCE_ORGANIZE_IMPORTS = "source.organizeImports";

  public CodeActionKind(String kind) {
    if (!kind.equals(CodeActionKind.QUICK_FIX) &&
        !kind.equals(CodeActionKind.REFACTOR) &&
        !kind.equals(CodeActionKind.REFACTOR_EXTRACT) &&
        !kind.equals(CodeActionKind.REFACTOR_INLINE) &&
        !kind.equals(CodeActionKind.REFACTOR_REWRITE) &&
        !kind.equals(CodeActionKind.SOURCE) &&
        !kind.equals(CodeActionKind.SOURCE_ORGANIZE_IMPORTS)) {
      throw new IllegalArgumentException(ILLEGAL_VALUE_ERROR_MSG_BASE + kind);
    }
    this.kind = kind;
  }

  public String getKind() {
    return kind;
  }

  public static CodeActionKind[] parseCodeActionKindJSONArray(JSONArray ops) {
    int entries = ops.length();
    CodeActionKind[] kinds = new CodeActionKind[entries];
    for (int i = 0; i < entries; i++) {
      kinds[i] = new CodeActionKind(ops.getString(i));
    }
    return kinds;
  }
}
