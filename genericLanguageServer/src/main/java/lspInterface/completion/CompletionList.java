package ablecLanguageServer;
/**
 * Represents a collection of [completion items](#CompletionItem) to be presented
 * in the editor.
 */
public class CompletionList implements LSPObject {
	/**
	 * This list it not complete. Further typing should result in recomputing
	 * this list.
	 */
	private Boolean isIncomplete;

	/**
	 * The completion items.
	 */
	private CompletionItem[] items;

  public CompletionList(CompletionItem[] items) {
    this.isIncomplete = false;
    this.items = items;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("isIncomplete", isIncomplete);
    json.addKeyValuePair("items", items);
    json.endObject();
    return json.getJson();
  }
}
