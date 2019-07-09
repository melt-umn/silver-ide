package ablecLanguageServer;
/**
 * Represents reasons why a text document is saved.
 */
public class TextDocumentSaveReason implements LSPObject {
  private int reason;
	/**
	 * Manually triggered, e.g. by the user pressing save, by starting debugging,
	 * or by an API call.
	 */
	public static final int MANUAL = 1;

	/**
	 * Automatic after a delay.
	 */
	public static final int AFTER_DELAY = 2;

	/**
	 * When the editor lost focus.
	 */
	public static final int FOCUS_OUT = 3;

  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 3;

  public TextDocumentSaveReason(int reason) {
    this.reason = reason; 
    if (!isValid()) {
      throw new IllegalArgumentException("Invalid value for TextDocumentSaveReason provided");
    }
  }

  public int getReason() {
    return reason;
  }

  public boolean isValid() {
    return reason >= MIN_VALUE && reason <= MAX_VALUE;
  }

  public String getJson() {
    return Integer.toString(reason);
  }
}
