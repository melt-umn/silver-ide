package genericLanguageServer.lspInterface.registration;

public class RenameRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * Renames should be checked and tested for validity before being executed.
	 */
	Boolean prepareProvider;
}
