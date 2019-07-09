package ablecLanguageServer;

import org.json.*;

public class FailureHandlingKind {
  private String kind;
 
  public static final String ABORT = "abort";
  public static final String TRANSACTIONAL = "transactional";
  public static final String UNDO = "undo";
  public static final String TEXT_ONLY_TRANSACTIONAL = "textOnlyTransactional";
  
  private static final String ILLEGAL_VALUE_ERROR_MSG_BASE =
    "FailureHandlingKind string must be one of\n " +
    ABORT + "\n" + 
    TRANSACTIONAL + "\n" + 
    UNDO + "\n" + 
    TEXT_ONLY_TRANSACTIONAL + "\n" + 
    "Instead it was: ";
  
  public FailureHandlingKind(String kind) {
    this.kind = kind;
    if (!isValid()) {
      throw new IllegalArgumentException(ILLEGAL_VALUE_ERROR_MSG_BASE + kind);
    }
  }  

  public String getKind() {
    return kind;
  }

  public boolean isValid() {
    return 
      kind.equals(ABORT) ||
      kind.equals(TRANSACTIONAL) ||
      kind.equals(UNDO) ||
      kind.equals(TEXT_ONLY_TRANSACTIONAL);
  }

  public static FailureHandlingKind[] parseFailureHandlingKindJSONArray(JSONArray ops) {
    int entries = ops.length();
    FailureHandlingKind[] kinds = new FailureHandlingKind[entries];
    for (int i = 0; i < entries; i++) {
      kinds[i] = new FailureHandlingKind(ops.getString(i));
    }
    return kinds;
  }
}
