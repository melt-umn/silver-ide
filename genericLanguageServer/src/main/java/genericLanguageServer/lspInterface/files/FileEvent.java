package genericLanguageServer.lspInterface.files;

import genericLanguageServer.lspInterface.document.DocumentUri;


import org.json.*;

/**
 * An event describing a file change.
 */
public class FileEvent {
	/**
	 * The file's URI.
	 */
	DocumentUri uri;
	/**
	 * The change type.
	 */
	FileChangeType type;

  public FileEvent(String uri, Integer type) {
    this.uri = new DocumentUri(uri);
    this.type = new FileChangeType(type);
  }

  public FileEvent(JSONObject obj) {
    this.uri = new DocumentUri(obj.getString("uri"));
    this.type = new FileChangeType(obj.getInt("type"));
  }

  public DocumentUri getUri() {
    return uri;
  }
}
