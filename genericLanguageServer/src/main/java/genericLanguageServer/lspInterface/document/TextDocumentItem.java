package genericLanguageServer.lspInterface.document;

import org.json.*;

public class TextDocumentItem {
	/**
	 * The text document's URI.
	 */
	DocumentUri uri;

	/**
	 * The text document's language identifier.
	 */
	String languageId;

	/**
	 * The version number of this document (it will increase after each
	 * change, including undo/redo).
	 */
	 Integer version;

	/**
	 * The content of the opened text document.
	 */
	String text;

  public TextDocumentItem(JSONObject obj) {
    if (obj.has("uri")) {
      uri = new DocumentUri(obj.getString("uri"));
    }
    if (obj.has("languageId")) {
      languageId = obj.getString("languageId");
    }
    if (obj.has("version")) {
      version = obj.getInt("version");
    }
    if (obj.has("text")) {
      text = obj.getString("text");
    }
  }

  public Integer getVersion() {
    return version;
  }

  public String getText() {
    return text;
  }

  public DocumentUri getUri() {
    return uri;
  }
}
