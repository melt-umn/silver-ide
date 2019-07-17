package genericLanguageServer.lspInterface.document;

import org.json.*;

public class TextDocumentIdentifier {
	/**
	 * The text document's URI.
	 */
	DocumentUri uri;

  public TextDocumentIdentifier(DocumentUri uri) {
    this.uri = uri;
  }

  public TextDocumentIdentifier(JSONObject params) {
    this.uri = new DocumentUri(params.getString("uri"));
  }

  public DocumentUri getIdentifier() {
    return uri;
  }
}
