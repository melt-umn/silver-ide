package ablecLanguageServer;

import org.json.*;

public class LocationLink implements LSPObject {

	/**
	 * Span of the origin of this link.
	 *
	 * Used as the underlined span for mouse interaction. Defaults to the word range at
	 * the mouse position.
	 */
	Range originSelectionRange;

	/**
	 * The target resource identifier of this link.
	 */
	DocumentUri targetUri;

	/**
	 * The full target range of this link. If the target for example is a symbol then target range is the
	 * range enclosing this symbol not including leading/trailing whitespace but everything else
	 * like comments. This information is typically used to highlight the range in the editor.
	 */
	Range targetRange;

	/**
	 * The range that should be selected and revealed when this link is being followed, e.g the name of a function.
	 * Must be contained by the the `targetRange`. See also `DocumentSymbol#range`
	 */
	Range targetSelectionRange;

  public LocationLink(JSONObject obj) {
    if (obj.has("originSelectionRange")) {
      originSelectionRange = new Range(obj.getJSONObject("originSelectionRange"));
    }
    if (obj.has("targetUri")) {
      targetUri = new DocumentUri(obj.getString("targetUri"));
    }
    if (obj.has("targetRange")) {
      targetRange = new Range(obj.getJSONObject("targetRange"));
    }
    if (obj.has("targetSelectionRange")) {
      targetSelectionRange = new Range(obj.getJSONObject("targetSelectionRange"));
    }
  }
 
  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("originSelectionRange", originSelectionRange);
    json.addKeyValuePair("targetUri", targetUri);
    json.addKeyValuePair("targetRange", targetRange);
    json.addKeyValuePair("targetSelectionRange", targetSelectionRange);
    json.endObject();
    return json.getJson();
  }
}
