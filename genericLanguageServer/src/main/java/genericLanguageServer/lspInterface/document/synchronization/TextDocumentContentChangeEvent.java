package genericLanguageServer.lspInterface.document.synchronization;

import genericLanguageServer.lspInterface.document.Range;

import org.json.*;
/**
 * An event describing a change to a text document. If range and rangeLength are omitted
 * the new text is considered to be the full content of the document.
 */
public class TextDocumentContentChangeEvent {
	/**
	 * The range of the document that changed.
	 */
	Range range;

	/**
	 * The length of the range that got replaced.
	 */
	Integer rangeLength;

	/**
	 * The new text of the range/document.
	 */
	String text;

  public TextDocumentContentChangeEvent(JSONObject obj) {
    if (obj.has("range")) {
      range = new Range(obj.getJSONObject("range"));
    }
    if (obj.has("rangeLength")) {
      rangeLength = obj.getInt("rangeLength");
    }
    if (obj.has("text")) {
      text = obj.getString("text");
    }
  }

  public static TextDocumentContentChangeEvent[] parseTextDocumentContentChangeEventJSONArray(JSONArray arr) {
    int entries = arr.length();
    TextDocumentContentChangeEvent[] events = new TextDocumentContentChangeEvent[entries];
    for (int i = 0; i < entries; i++) {
      events[i] = new TextDocumentContentChangeEvent(arr.getJSONObject(i));
    }
    return events;
  }
}
