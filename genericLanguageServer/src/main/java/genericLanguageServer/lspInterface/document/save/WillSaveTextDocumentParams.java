package genericLanguageServer.lspInterface.document.save;

import genericLanguageServer.lspInterface.document.TextDocumentIdentifier;
import genericLanguageServer.lspInterface.document.DocumentUri;

import org.json.*;

/**
 * The parameters send in a will save text document notification.
 */
public class WillSaveTextDocumentParams {
	/**
	 * The document that will be saved.
	 */
	private TextDocumentIdentifier textDocument;

	/**
	 * The 'TextDocumentSaveReason'.
	 */
	private TextDocumentSaveReason reason;

  public WillSaveTextDocumentParams(JSONObject params) {
    if (params.has("textDocument")) {
      textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
    }
    if (params.has("reason")) {
      reason = new TextDocumentSaveReason(params.getInt("reason"));
    }
  }

  public DocumentUri getDocument() {
    return textDocument.getIdentifier();
  }
}
