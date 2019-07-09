package ablecLanguageServer;

public class PublishDiagnosticsParams implements LSPObject {
	/**
	 * The URI for which diagnostic information is reported.
	 */
	DocumentUri uri;

	/**
	 * An array of diagnostic information items.
	 */
	Diagnostic[] diagnostics;

  public PublishDiagnosticsParams(DocumentUri uri, Diagnostic[] diagnostics) {
    this.uri = uri;
    this.diagnostics = diagnostics;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("uri", uri);
    json.addKeyValuePair("diagnostics", diagnostics);
    json.endObject();
    return json.getJson();
  }
}
