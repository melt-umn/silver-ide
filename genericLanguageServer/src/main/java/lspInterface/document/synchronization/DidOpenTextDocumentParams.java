package ablecLanguageServer;

import org.json.*;

public class DidOpenTextDocumentParams {
	/**
	 * The document that was opened.
	 */
	TextDocumentItem textDocument;

  public DidOpenTextDocumentParams(JSONObject params) {
    textDocument = new TextDocumentItem(params.getJSONObject("textDocument"));
  }

  public DocumentUri getDocument() {
    return textDocument.getUri();
  }
}
