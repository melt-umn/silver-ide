package ablecLanguageServer;

import org.json.*;

public class DocumentFormattingParams {
	/**
	 * The document to format.
	 */
	TextDocumentIdentifier textDocument;

	/**
	 * The format options.
	 */
	FormattingOptions options;

  public DocumentFormattingParams(JSONObject params) {
    if (params.has("textDocument")) {
      textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
    }
    if (params.has("options")) {
      options = new FormattingOptions(params.getJSONObject("options"));
    }
  }

  public DocumentUri getDocument() {
    if (textDocument != null) {
      return textDocument.getIdentifier();
    }
    return null;
  }
}

