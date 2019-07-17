package genericLanguageServer.lspInterface.completion;

import genericLanguageServer.lspInterface.LSPObject;

import org.json.*;

public class CompletionItemKind implements LSPObject {
  private int kind;

	public static final int TEXT = 1;
	public static final int METHOD = 2;
	public static final int FUNCTION = 3;
	public static final int CONSTRUCTOR = 4;
	public static final int FIELD = 5;
	public static final int VARIABLE = 6;
	public static final int CLASS = 7;
	public static final int INTERFACE = 8;
	public static final int MODULE = 9;
	public static final int PROPERTY = 10;
	public static final int UNIT = 11;
	public static final int VALUE = 12;
	public static final int ENUM = 13;
	public static final int KEYWORD = 14;
	public static final int SNIPPET = 15;
	public static final int COLOR = 16;
	public static final int FILE = 17;
	public static final int REFERENCE = 18;
	public static final int FOLDER = 19;
	public static final int ENUM_MEMBER = 20;
	public static final int CONSTANT = 21;
	public static final int STRUCT = 22;
	public static final int EVENT = 23;
	public static final int OPERATOR = 24;
	public static final int TYPE_PARAMETER = 25;

  private static int MIN_SYMBOL = 1;
  private static int MAX_SYMBOL = 25;

  public CompletionItemKind(Integer kind) {
    if (kind < MIN_SYMBOL || kind > MAX_SYMBOL) {
      throw new IllegalArgumentException("CompletionItemKind out of range");
    } else {
      this.kind = kind;
    }
  }

  public int getKind() {
    return kind;
  }

  public static CompletionItemKind[] parseCompletionItemKindJSONArray(JSONArray ops) {
    int entries = ops.length();
    CompletionItemKind[] kinds = new CompletionItemKind[entries];
    for (int i = 0; i < entries; i++) {
      kinds[i] = new CompletionItemKind(ops.getInt(i));
    }
    return kinds;
  }

  public String getJson() {
    return Integer.toString(kind);
  }
}
