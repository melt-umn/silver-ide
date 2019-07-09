package ablecLanguageServer;
/**
 * A document highlight is a range inside a text document which deserves
 * special attention. Usually a document highlight is visualized by changing
 * the background color of its range.
 *
 */
public class DocumentHighlight implements LSPObject {
	/**
	 * The range this highlight applies to.
	 */
	Range range;

	/**
	 * The highlight kind, default is DocumentHighlightKind.Text.
	 */
	DocumentHighlightKind kind; // integer wrapper

  public DocumentHighlight(Range range, DocumentHighlightKind kind) {
    this.range = range;
    this.kind = kind;
  }

  public DocumentHighlight(Range range, Integer kind) {
    this.range = range;
    this.kind = new DocumentHighlightKind(kind);
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("range", range);
    json.addKeyValuePair("kind", kind);
    json.endObject();
    return json.getJson();
  }
}
