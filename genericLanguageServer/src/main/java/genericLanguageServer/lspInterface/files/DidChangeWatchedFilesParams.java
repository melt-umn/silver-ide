package genericLanguageServer.lspInterface.files;

import genericLanguageServer.lspInterface.document.DocumentUri;

import org.json.*;

public class DidChangeWatchedFilesParams {
	/**
	 * The actual file events.
	 */
	FileEvent[] changes;

  public DidChangeWatchedFilesParams(FileEvent[] changes) {
    this.changes = changes;
  }

  public DidChangeWatchedFilesParams(JSONObject obj) {
    JSONArray arr = obj.getJSONArray("changes");
    int numChanges = arr.length();
    changes = new FileEvent[numChanges];
    for (int i = 0; i < numChanges; i++) {
      changes[i] = new FileEvent(arr.getJSONObject(i));
    }
  }

  public DocumentUri[]  getChangedDocuments() {
    DocumentUri[] changedDocs = new DocumentUri[changes.length];
    for (int i = 0; i < changes.length; i++) {
      changedDocs[i] = changes[i].getUri();
    }
    return changedDocs;
  }
}
