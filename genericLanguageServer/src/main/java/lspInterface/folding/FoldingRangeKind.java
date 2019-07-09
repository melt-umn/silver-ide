package ablecLanguageServer;
/**
 * Enum of known range kinds
 */
public class FoldingRangeKind implements LSPObject {
  private String kind;

	/**
	 * Folding range for a comment
	 */
	public static final String COMMENT = "comment";
	/**
	 * Folding range for a imports or includes
	 */
	public static final String IMPORTS = "imports";
	/**
	 * Folding range for a region (e.g. `#region`)
	 */
	public static final String REGION = "region";

  private static final String ILLEGAL_VALUE_ERROR_MSG_BASE = 
    "FoldingRangeKind string must be one of\n " +
    COMMENT + "\n" +
    IMPORTS + "\n" + 
    REGION + "\n" +
    "Instead it was: ";

  public FoldingRangeKind(String kind) {
    this.kind = kind; 
    if (!isValid()) {
      throw new IllegalArgumentException(ILLEGAL_VALUE_ERROR_MSG_BASE + kind);
    }
  }

  public boolean isValid() {
    return
      kind.equals(COMMENT) || 
      kind.equals(IMPORTS) || 
      kind.equals(REGION);
  }

  public String getKind() {
    return kind;
  }

  public String getJson() {
    return "\"" + kind + "\"";
  }
}
