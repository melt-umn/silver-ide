package genericLanguageServer.lspInterface.registration;

import genericLanguageServer.lspInterface.document.DocumentSelector;

public class TextDocumentRegistrationOptions {
	/**
	 * A document selector to identify the scope of the registration. If set to null
	 * the document selector provided on the client side will be used.
	 */
	DocumentSelector documentSelector;

  public TextDocumentRegistrationOptions() {
  }

  public TextDocumentRegistrationOptions(DocumentSelector selector) {
    this.documentSelector = selector;
  }
}
