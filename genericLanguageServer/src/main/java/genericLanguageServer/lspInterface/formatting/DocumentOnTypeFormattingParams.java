package genericLanguageServer.lspInterface.formatting;

import genericLanguageServer.lspInterface.document.Position;
import genericLanguageServer.lspInterface.document.DocumentUri;
import genericLanguageServer.lspInterface.document.TextDocumentIdentifier;
import org.json.*;

public class DocumentOnTypeFormattingParams {
	/**
	 * The document to format.
	 */
	TextDocumentIdentifier textDocument;

	/**
	 * The position at which this request was sent.
	 */
	Position position;

	/**
	 * The character that has been typed.
	 */
	String ch;

	/**
	 * The format options.
	 */
	FormattingOptions options;

  public DocumentOnTypeFormattingParams(JSONObject params) {
    if (params.has("textDocument")) {
      textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
    }
    if (params.has("position")) {
      position = new Position(params.getJSONObject("position"));
    }
    if (params.has("ch")) {
      ch = params.getString("ch");
    }
    if (params.has("options")) {
      options = new FormattingOptions(params.getJSONObject("options"));
    }
  }

  public DocumentUri getDocument() {
    return textDocument.getIdentifier();
  }

  public Position getPosition() {
    return position;
  }

  public String getTypedCharacter() {
    return ch;
  }
}
