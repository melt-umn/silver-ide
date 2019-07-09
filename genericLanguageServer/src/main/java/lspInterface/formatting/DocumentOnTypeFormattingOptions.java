package ablecLanguageServer;
/**
 * Format document on type options.
 */
public class DocumentOnTypeFormattingOptions implements LSPObject {
	/**
	 * A character on which formatting should be triggered, like `}`.
	 */
	String firstTriggerCharacter;

	/**
	 * More trigger characters.
	 */
	String[] moreTriggerCharacter;

  public DocumentOnTypeFormattingOptions(String firstTriggerChar) {
    this.firstTriggerCharacter = firstTriggerChar;
  }

  public DocumentOnTypeFormattingOptions(String firstTriggerChar, String[] restTriggerChars) {
    this.firstTriggerCharacter = firstTriggerChar;
    this.moreTriggerCharacter = restTriggerChars;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("firstTriggerCharacter", firstTriggerCharacter);
    json.addKeyValuePair("moreTriggerCharacter", moreTriggerCharacter);
    json.endObject();
    return json.getJson();
  }
}

