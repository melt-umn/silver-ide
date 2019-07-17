package genericLanguageServer.lspInterface.messages;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;

public class ShowMessageParams implements LSPObject {
	/**
	 * The message type.
	 */
	private MessageType type;

	/**
	 * The actual message.
	 */
	private String message;

  public ShowMessageParams(Integer type, String msg) {
    this.message = msg;
    this.type = new MessageType(type);
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("type", type);
    json.addKeyValuePair("message", message);
    json.endObject();
    return json.getJson();
  }

  public String getMessage() {
    return message;
  }
}
