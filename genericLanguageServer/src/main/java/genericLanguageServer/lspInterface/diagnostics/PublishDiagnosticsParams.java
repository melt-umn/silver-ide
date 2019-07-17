package genericLanguageServer.lspInterface.diagnostics;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;
import genericLanguageServer.lspInterface.document.DocumentUri;


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
