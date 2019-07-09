package ablecLanguageServer;
  
public class CompletionItem implements LSPObject {
	/**
	 * The label of this completion item. By default
	 * also the text that is inserted when selecting
	 * this completion.
	 */
	private String label;

	/**
	 * The kind of this completion item. Based of the kind
	 * an icon is chosen by the editor. The standardized set
	 * of available values is defined in `CompletionItemKind`.
	 */
	private CompletionItemKind kind; //: number;

	/**
	 * A human-readable string with additional information
	 * about this item, like type or symbol information.
	 */
	private String detail;

	/**
	 * A human-readable string that represents a doc-comment.
	 */
	private String documentation;

	/**
	 * Indicates if this item is deprecated.
	 */
	private Boolean deprecated;

	/**
	 * Select this item when showing.
	 *
	 * *Note* that only one completion item can be selected and that the
	 * tool / client decides which item that is. The rule is that the *first*
	 * item of those that match best is selected.
	 */
	private Boolean preselect;

	/**
	 * A string that should be used when comparing this item
	 * with other items. When `falsy` the label is used.
	 */
	private String sortText;

	/**
	 * A string that should be used when filtering a set of
	 * completion items. When `falsy` the label is used.
	 */
	private String filterText;

	/**
	 * A string that should be inserted into a document when selecting
	 * this completion. When `falsy` the label is used.
	 *
	 * The `insertText` is subject to interpretation by the client side.
	 * Some tools might not take the string literally. For example
	 * VS Code when code complete is requested in this example `con<cursor position>`
	 * and a completion item with an `insertText` of `console` is provided it
	 * will only insert `sole`. Therefore it is recommended to use `textEdit` instead
	 * since it avoids additional client side interpretation.
	 */
	private String insertText;

	/**
	 * The format of the insert text. The format applies to both the `insertText` property
	 * and the `newText` property of a provided `textEdit`.
	 */
	private InsertTextFormat insertTextFormat;

	/**
	 * An edit which is applied to a document when selecting this completion. When an edit is provided the value of
	 * `insertText` is ignored.
	 *
	 * *Note:* The range of the edit must be a single line range and it must contain the position at which completion
	 * has been requested.
	 */
	private TextEdit textEdit;

	/**
	 * An optional array of additional text edits that are applied when
	 * selecting this completion. Edits must not overlap (including the same insert position)
	 * with the main edit nor with themselves.
	 *
	 * Additional text edits should be used to change text unrelated to the current cursor position
	 * (for example adding an import statement at the top of the file if the completion item will
	 * insert an unqualified type).
	 */
	private TextEdit[] additionalTextEdits;

	/**
	 * An optional set of characters that when pressed while this completion is active will accept it first and
	 * then type that character. *Note* that all commit characters should have `length=1` and that superfluous
	 * characters will be ignored.
	 */
	private String[] commitCharacters;

	/**
	 * An optional command that is executed *after* inserting this completion. *Note* that
	 * additional modifications to the current document should be described with the
	 * additionalTextEdits-property.
	 */
	private Command command;

	/**
	 * A data entry field that is preserved on a completion item between
	 * a completion and a completion resolve request.
	 */
	private Object data;
  
  // the only required item
  public CompletionItem(String label) {
    this.label = label;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("label", label);
    json.addKeyValuePair("kind", kind);
    json.addKeyValuePair("detail", detail);
    json.addKeyValuePair("deprecated", deprecated);
    json.addKeyValuePair("preselect", preselect);
    json.addKeyValuePair("sortText", sortText);
    json.addKeyValuePair("filterText", filterText);
    json.addKeyValuePair("insertText", insertText);
    json.addKeyValuePair("insertTextFormat", insertTextFormat);
    json.addKeyValuePair("textEdit", textEdit);
    json.addKeyValuePair("additionalTextEdits", additionalTextEdits);
    json.addKeyValuePair("commitCharacters", commitCharacters);
    json.addKeyValuePair("command", command);
    //json.addKeyValuePair("data", data);
    json.endObject();
    return json.getJson();
  }
}

