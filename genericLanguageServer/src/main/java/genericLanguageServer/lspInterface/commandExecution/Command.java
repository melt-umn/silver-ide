package genericLanguageServer.lspInterface.commandExecution;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;

public class Command implements LSPObject {
	/**
	 * Title of the command, like `save`.
	 */
	private String title;
	/**
	 * The identifier of the actual command handler.
	 */
	private String command;
	/**
	 * Arguments that the command handler should be
	 * invoked with.
	 */
	private Object[] arguments;

  public Command(String title, String command) {
    this.title = title;
    this.command = command;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("title", title);
    json.addKeyValuePair("command", command);
    //json.addKeyValuePair("arguments", arguments);
    json.endObject();
    return json.getJson();
  }
}
