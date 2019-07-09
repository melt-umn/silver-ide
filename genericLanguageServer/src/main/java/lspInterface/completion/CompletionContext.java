package ablecLanguageServer;

import org.json.*;

/**
 * Contains additional information about the context in which a completion request is triggered.
 */
public class CompletionContext implements LSPObject {
	/**
	 * How the completion was triggered.
	 */
	private CompletionTriggerKind triggerKind;

	/**
	 * The trigger character (a single character) that has trigger code complete.
	 * Is undefined if `triggerKind !== CompletionTriggerKind.TriggerCharacter`
	 */
	private String triggerCharacter;

  public CompletionContext(JSONObject obj) {
    if (obj.has("triggerKind")) {
      triggerKind = new CompletionTriggerKind(obj.getInt("triggerKind"));
    }
    if (obj.has("triggerCharacter")) {
      triggerCharacter = obj.getString("triggerCharacter");
    }
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("triggerKind", triggerKind);
    json.addKeyValuePair("triggerCharacter", triggerCharacter);
    json.endObject();
    return json.getJson();
  }
}
