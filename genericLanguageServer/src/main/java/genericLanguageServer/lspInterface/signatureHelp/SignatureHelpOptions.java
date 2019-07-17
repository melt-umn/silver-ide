package genericLanguageServer.lspInterface.signatureHelp;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;

/**
 * Signature help options.
 */
public class SignatureHelpOptions implements LSPObject {
	/**
	 * The characters that trigger signature help
	 * automatically.
	 */
	private String[] triggerCharacters;

  public SignatureHelpOptions() {
    triggerCharacters = new String[0];
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("triggerCharacters", triggerCharacters);
    json.endObject();
    return json.getJson();
  }
}
