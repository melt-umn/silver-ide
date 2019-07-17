package genericLanguageServer.lspInterface.formatting;

import genericLanguageServer.lspInterface.document.Range;
import genericLanguageServer.lspInterface.document.DocumentUri;
import genericLanguageServer.lspInterface.document.TextDocumentIdentifier;

import org.json.*;

public class DocumentRangeFormattingParams {
	/**
	 * The document to format.
	 */
	TextDocumentIdentifier textDocument;

	/**
	 * The range to format
	 */
	Range range;

	/**
	 * The format options
	 */
	FormattingOptions options;

  public DocumentRangeFormattingParams(JSONObject params) {
    if (params.has("textDocument")) {
      textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
    }
    if (params.has("range")) {
      range = new Range(params.getJSONObject("range"));
    }
    if (params.has("options")) {
      options = new FormattingOptions(params.getJSONObject("options"));
    }
  }

  public DocumentUri getDocument() {
    return textDocument.getIdentifier();
  }

  public Range getRange() {
    return range;
  }
}
