package genericLanguageServer.lspInterface.completion;

import genericLanguageServer.lspInterface.LSPObject;

import org.json.*;

/**
 * How a completion was triggered
 */
public class CompletionTriggerKind implements LSPObject {
  private int kind;
	/**
	 * Completion was triggered by typing an identifier (24x7 code
	 * complete), manual invocation (e.g Ctrl+Space) or via API.
	 */
	public static final int INVOKED = 1;

	/**
	 * Completion was triggered by a trigger character specified by
	 * the `triggerCharacters` properties of the `CompletionRegistrationOptions`.
	 */
	public static final int TRIGGER_CHARACTER = 2;

	/**
	 * Completion was re-triggered as the current completion list is incomplete.
	 */
	public static final int TRIGGER_FOR_INCOMPLETE_COMPLETIONS = 3;


  private static int MIN_SYMBOL = 1;
  private static int MAX_SYMBOL = 3;

  public CompletionTriggerKind(Integer kind) {
    if (kind < MIN_SYMBOL || kind > MAX_SYMBOL) {
      throw new IllegalArgumentException("CompletionTriggerKind out of range");
    } else {
      this.kind = kind;
    }
  }

  public int getKind() {
    return kind;
  }

  public String getJson() {
    return Integer.toString(kind);
  }

}
