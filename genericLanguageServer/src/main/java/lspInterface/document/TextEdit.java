package ablecLanguageServer;
public class TextEdit implements LSPObject {
	/**
	 * The range of the text document to be manipulated. To insert
	 * text into a document create a range where start === end.
	 */
	Range range;

	/**
	 * The string to be inserted. For delete operations use an
	 * empty string.
	 */
	String newText;

  public TextEdit(Range range, String newText) {
    this.range = range;
    this.newText = newText;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("range", range);
    json.addKeyValuePair("newText", newText);
    json.endObject();
    return json.getJson();
  }
}
