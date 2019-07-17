package genericLanguageServer.lspInterface.highlighting;

import genericLanguageServer.lspInterface.LSPObject;
/**
 * A document highlight kind.
 */
public class DocumentHighlightKind implements LSPObject {
  private int kind;
	/**
	 * A textual occurrence.
	 */
	public static final int TEXT = 1;

	/**
	 * Read-access of a symbol, like reading a variable.
	 */
	public static final int READ = 2;

	/**
	 * Write-access of a symbol, like writing to a variable.
	 */
	public static final int WRITE = 3;

  private static int MIN_SYMBOL = 1;
  private static int MAX_SYMBOL = 3;

  public DocumentHighlightKind(int kind) {
    if (kind < MIN_SYMBOL || kind > MAX_SYMBOL) {
      throw new IllegalArgumentException("DocumentHighlightKind out of range");
    } else {
      this.kind = kind;
    }
  }

  public int getKind() {
    return kind;
  }

  public String getJson() {
    return Integer.toString(kind);
  }

}
