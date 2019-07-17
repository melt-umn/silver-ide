package genericLanguageServer.lspInterface.messages;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;

public class LogMessageParams implements LSPObject {
	/**
	 * The message type.
	 */
	private MessageType type;

	/**
	 * The actual message
	 */
	private String message;

  public LogMessageParams(Integer type, String message) {
    this.type = new MessageType(type);
    this.message = message;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("type", type);
    json.addKeyValuePair("message", message);
    json.endObject();
    return json.getJson();
  }

}
