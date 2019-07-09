package ablecLanguageServer;
/**
 * Save options.
 */
public class SaveOptions implements LSPObject{
	/**
	 * The client is supposed to include the content on save.
	 */
	Boolean includeText;

  // default save options
  public SaveOptions() {
    includeText = true;
  }

  public SaveOptions(Boolean includeText) {
    this.includeText = includeText;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("includeText", includeText);
    json.endObject();
    return json.getJson();
  }
}

