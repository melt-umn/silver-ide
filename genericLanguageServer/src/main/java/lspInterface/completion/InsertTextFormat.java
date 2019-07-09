package ablecLanguageServer;

import org.json.*;

/**
 * Defines whether the insert text in a completion item should be interpreted as
 * plain text or a snippet.
 */
public class InsertTextFormat implements LSPObject {
  private int format;
	/**
	 * The primary text to be inserted is treated as a plain string.
	 */
	public static final int PLAIN_TEXT = 1;

	/**
	 * The primary text to be inserted is treated as a snippet.
	 *
	 * A snippet can define tab stops and placeholders with `$1`, `$2`
	 * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
	 * the end of the snippet. Placeholders with equal identifiers are linked,
	 * that is typing in one will update others too.
	 */
	public static final int SNIPPET = 2;

  private static int MIN_SYMBOL = 1;
  private static int MAX_SYMBOL = 2;

  public InsertTextFormat(Integer format) {
    if (format > MAX_SYMBOL || format < MIN_SYMBOL) {
      throw new IllegalArgumentException("InsertTextFormat out of range");
    } else {
      this.format = format;
    }
  }

  public int getFormat() {
    return format;
  }

  public String getJson() {
    return Integer.toString(format);
  }
}
