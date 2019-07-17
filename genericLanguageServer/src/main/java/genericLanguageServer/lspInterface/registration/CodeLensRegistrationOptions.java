package genericLanguageServer.lspInterface.registration;

public class CodeLensRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * Code lens has a resolve provider as well.
	 */
	Boolean resolveProvider;
}
