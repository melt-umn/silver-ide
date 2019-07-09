package ablecLanguageServer;

import org.json.*;

public class TextDocumentPositionParams {
	/**
	 * The text document.
	 */
	TextDocumentIdentifier textDocument;

	/**
	 * The position inside the text document.
	 */
	Position position;

  public TextDocumentPositionParams(JSONObject params) {
    textDocument = new TextDocumentIdentifier(params.getJSONObject("textDocument"));
    position = new Position(params.getJSONObject("position"));
  }

  public Position getPosition() {
    return position;
  }

  public DocumentUri getDocument() {
    return textDocument.getIdentifier();
  }
}
