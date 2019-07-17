package genericLanguageServer.lspInterface.registration;

public class DocumentOnTypeFormattingRegistrationOptions extends TextDocumentRegistrationOptions {
	/**
	 * A character on which formatting should be triggered, like `}`.
	 */
	String firstTriggerCharacter;
	/**
	 * More trigger characters.
	 */
	String[] moreTriggerCharacter;
}
