package genericLanguageServer.lspInterface.document.save;

import genericLanguageServer.lspInterface.document.TextDocumentIdentifier;
import genericLanguageServer.lspInterface.document.DocumentUri;

import org.json.*;

public class DidSaveTextDocumentParams {
	/**
	 * The document that was saved.
	 */
	private TextDocumentIdentifier textDocument;

	/**
	 * Optional the content when saved. Depends on the includeText value
	 * when the save notification was requested.
	 */
	private String text;

  public DidSaveTextDocumentParams(JSONObject obj) {
    if (obj.has("textDocument")) {
      textDocument = new TextDocumentIdentifier(obj.getJSONObject("textDocument"));
    }
    if (obj.has("text")) {
      text = obj.getString("text");
    }
  }

  public DocumentUri getDocument() {
    return textDocument.getIdentifier();
  }
}
