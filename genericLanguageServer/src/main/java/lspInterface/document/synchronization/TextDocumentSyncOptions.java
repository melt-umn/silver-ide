package ablecLanguageServer;
/**
 * Defines how the host (editor) should sync document changes to the language server.
 */
public class TextDocumentSyncOptions implements LSPObject {
  /**
	 * Open and close notifications are sent to the server. If omitted open close notification should not
	 * be sent.
	 */
	private Boolean openClose;
 	/**
	 * Change notifications are sent to the server. See TextDocumentSyncKind.None, TextDocumentSyncKind.Full
	 * and TextDocumentSyncKind.Incremental. If omitted it defaults to TextDocumentSyncKind.None.
	 */
	private TextDocumentSyncKind change;
	/**
	 * If present will save notifications are sent to the server. If omitted the notification should not be
	 * sent.
	 */
	private Boolean willSave;
	/**
	 * If present will save wait until requests are sent to the server. If omitted the request should not be
	 * sent.
	 */
	private Boolean willSaveWaitUntil;
	/**
	 * If present save notifications are sent to the server. If omitted the notification should not be
	 * sent.
	 */
	private SaveOptions save;

  // text document sync options
  public TextDocumentSyncOptions() {
    openClose = true;
    change = new TextDocumentSyncKind(TextDocumentSyncKind.INCREMENTAL);
    Server server = Server.getServer();
    if (server.clientSendsWillSaveNotification()) {
      willSave = true;
      save = new SaveOptions();
    }
    willSaveWaitUntil = server.clientSendsWillSaveWaitUntilRequest();
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("openClose", openClose);
    json.addKeyValuePair("change", change);
    json.addKeyValuePair("willSave", willSave);
    json.addKeyValuePair("willSaveWaitUntil", willSaveWaitUntil);
    json.addKeyValuePair("save", save);
    json.endObject();
    return json.getJson();
  }
}
