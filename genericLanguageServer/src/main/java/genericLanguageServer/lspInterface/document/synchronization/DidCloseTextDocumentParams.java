package genericLanguageServer.lspInterface.document.synchronization;

import genericLanguageServer.lspInterface.document.TextDocumentIdentifier;
import genericLanguageServer.lspInterface.document.DocumentUri;

import org.json.*;

public class DidCloseTextDocumentParams {
	/**
	 * The document that was closed.
	 */
	private TextDocumentIdentifier textDocument;

  public DidCloseTextDocumentParams(JSONObject params) {
    if (params.has("textDocument")) {
      textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
    }
  }

  public DocumentUri getDocument() {
    return textDocument.getIdentifier();
  }
}
