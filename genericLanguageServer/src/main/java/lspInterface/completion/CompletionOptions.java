package ablecLanguageServer;
public class CompletionOptions implements LSPObject {
  /**
	 * The server provides support to resolve additional
	 * information for a completion item.
	 */
	 Boolean resolveProvider;

	/**
	 * The characters that trigger completion automatically.
	 */
	String[] triggerCharacters;

  public CompletionOptions() {
    resolveProvider = false;
    triggerCharacters = new String[0];
  }

  public CompletionOptions(Boolean resolveProvider, String[] triggerCharacters) {
    this.resolveProvider = resolveProvider;
    this.triggerCharacters = triggerCharacters;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("resolveProvider", resolveProvider);
    json.addKeyValuePair("triggerCharacters", triggerCharacters);
    json.endObject();
    return json.getJson();
  }
}
