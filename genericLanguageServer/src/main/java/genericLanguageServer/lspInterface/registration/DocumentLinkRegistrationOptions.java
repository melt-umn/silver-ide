package genericLanguageServer.lspInterface.registration;

public class DocumentLinkRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * Document links have a resolve provider as well.
	 */
	Boolean resolveProvider;
}
