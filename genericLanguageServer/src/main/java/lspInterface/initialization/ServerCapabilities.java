package ablecLanguageServer;

public class ServerCapabilities implements LSPObject {
	/**
	 * Defines how text documents are synced. 
	 */
  TextDocumentSyncOptions textDocumentSync;
	/**
	 * The server provides hover support.
	 */
  Boolean hoverProvider;
	/**
	 * The server provides completion support.
	 */
  CompletionOptions completionProvider;
	/**
	 * The server provides signature help support.
	 */
  SignatureHelpOptions signatureHelpProvider;
	/**
	 * The server provides goto definition support.
	 */
  Boolean definitionProvider;
	/**
	 * The server provides Goto Type Definition support.
	 */
  Boolean typeDefinitionProvider; 
	/**
	 * The server provides Goto Implementation support.
	 */
  Boolean implementationProvider;
	/**
	 * The server provides find references support.
	 */
  Boolean referencesProvider;
	/**
	 * The server provides document highlight support.
	 */
  Boolean documentHighlightProvider;
	/**
	 * The server provides document symbol support.
	 */
  Boolean documentSymbolProvider;
	/**
	 * The server provides workspace symbol support.
	 */
  Boolean workspaceSymbolProvider;
	/**
	 * The server provides code actions. The `CodeActionOptions` return type is only
	 * valid if the client signals code action literal support via the property
	 * `textDocument.codeAction.codeActionLiteralSupport`.
	 */
  CodeActionOptions codeActionProvider; // may also be boolean
 	/**
	 * The server provides code lens.
	 */
	CodeLensOptions codeLensProvider;
	/**
	 * The server provides document formatting.
	 */
	Boolean documentFormattingProvider;
	/**
	 * The server provides document range formatting.
	 */
	Boolean documentRangeFormattingProvider;
	/**
	 * The server provides document formatting on typing.
	 */
	DocumentOnTypeFormattingOptions documentOnTypeFormattingProvider;
	/**
	 * The server provides rename support. RenameOptions may only be
	 * specified if the client states that it supports
	 * `prepareSupport` in its initial `initialize` request.
	 */
	RenameOptions renameProvider; // could also be a boolean
 	/**
	 * The server provides document link support.
	 */
	DocumentLinkOptions documentLinkProvider;
  /**
	 * The server provides color provider support.
	 */
	ColorProviderOptions colorProvider;
	/**
	 * The server provides folding provider support.
	 */
	FoldingRangeProviderOptions foldingRangeProvider;

  /**
	 * The server provides go to declaration support.
	 */
	Boolean declarationProvider;
	/**
	 * The server provides execute command support.
	 */
	ExecuteCommandOptions executeCommandProvider;

	/**
	 * Workspace specific server capabilities
	 */
  Workspace workspace;

  class Workspace {
		/**
		 * The server supports workspace folder.
		 */
    WorkspaceFolders workspaceFolders;
    class WorkspaceFolders {
			/**
			* The server has support for workspace folders
			*/
      Boolean supported;
			/**
			* Whether the server wants to receive workspace folder
			* change notifications.
			*
			* If a strings is provided the string is treated as a ID
			* under which the notification is registered on the client
			* side. The ID can be used to unregister for these events
			* using the `client/unregisterCapability` request.
			*/
			Boolean changeNotifications; // note this can also be a string
		}
  }

  // construct a server with default capabilities
  public ServerCapabilities() {
    // current default capabilities is everything is null except implemented stuff
    hoverProvider = true;
    textDocumentSync = new TextDocumentSyncOptions();
    completionProvider = new CompletionOptions();
    signatureHelpProvider = new SignatureHelpOptions();
    documentFormattingProvider = true;
    documentRangeFormattingProvider = true;
	  documentOnTypeFormattingProvider = new DocumentOnTypeFormattingOptions(":");
    definitionProvider = true;
	  declarationProvider = true;
    typeDefinitionProvider = true; 
    implementationProvider = true;
    referencesProvider = true;
    documentHighlightProvider = true;
    // atom doesn't support 
	  //foldingRangeProvider = new FoldingRangeProviderOptions();
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("textDocumentSync", textDocumentSync);
    json.addKeyValuePair("completionProvider", completionProvider);
    json.addKeyValuePair("signatureHelpProvider", signatureHelpProvider);
    json.addKeyValuePair("hoverProvider", hoverProvider);
    json.addKeyValuePair("documentFormattingProvider", documentFormattingProvider);
	  json.addKeyValuePair("documentRangeFormattingProvider", documentRangeFormattingProvider);
	  json.addKeyValuePair("documentOnTypeFormattingProvider", documentOnTypeFormattingProvider);
    json.addKeyValuePair("foldingRangeProvider", foldingRangeProvider);
	  json.addKeyValuePair("declarationProvider", declarationProvider);
	  json.addKeyValuePair("definitionProvider", definitionProvider);
	  json.addKeyValuePair("typeDefinitionProvider", typeDefinitionProvider);
	  json.addKeyValuePair("implementationProvider", implementationProvider);
	  json.addKeyValuePair("referencesProvider", referencesProvider);
	  json.addKeyValuePair("documentHighlightProvider", documentHighlightProvider);
    json.endObject();
    return json.getJson();
  }
}
