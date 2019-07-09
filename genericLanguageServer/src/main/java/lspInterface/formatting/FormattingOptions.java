package ablecLanguageServer;

import org.json.*;

/**
 * Value-object describing what options formatting should use.
 */
public class FormattingOptions {
	/**
	 * Size of a tab in spaces.
	 */
	Integer tabSize;

	/**
	 * Prefer spaces over tabs.
	 */
	Boolean insertSpaces;

	/**
	 * Signature for further properties.
	 */
	//[key: string]: boolean | number | string;

  public FormattingOptions(JSONObject obj) {
    if (obj.has("tabSize")) {
      tabSize = obj.getInt("tabSize");
    }
    if (obj.has("insertSpaces")) {
      insertSpaces = obj.getBoolean("insertSpaces");
    }
  }
}
