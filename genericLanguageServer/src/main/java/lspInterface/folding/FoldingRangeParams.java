package ablecLanguageServer;

import org.json.*;

public class FoldingRangeParams {
	/**
	 * The text document.
	 */
	TextDocumentIdentifier textDocument;

  public FoldingRangeParams(JSONObject params) {
    this.textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
  }
}

