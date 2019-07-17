package genericLanguageServer.lspInterface;

import org.json.*;

public class SymbolKind {
  private int kind;

	public static final int FILE = 1;
	public static final int MODULE = 2;
	public static final int NAMESPACE = 3;
	public static final int PACKAGE = 4;
	public static final int CLASS = 5;
	public static final int METHOD = 6;
	public static final int PROPERTY = 7;
	public static final int FIELD = 8;
	public static final int CONSTRUCTOR = 9;
	public static final int ENUM = 10;
	public static final int INTERFACE = 11;
	public static final int FUNCTION = 12;
	public static final int VARIABLE = 13;
	public static final int CONSTANT = 14;
	public static final int STRING = 15;
	public static final int NUMBER = 16;
	public static final int BOOLEAN = 17;
	public static final int ARRAY = 18;
	public static final int OBJECT = 19;
	public static final int KEY = 20;
	public static final int NULL = 21;
	public static final int ENUM_MEMBER = 22;
	public static final int STRUCT = 23;
	public static final int EVENT = 24;
	public static final int OPERATOR = 25;
	public static final int TYPE_PARAMETER = 26;

  private static int MIN_SYMBOL = 1;
  private static int MAX_SYMBOL = 26;

  public SymbolKind(Integer kind) {
    if (kind >= SymbolKind.MIN_SYMBOL && kind <= SymbolKind.MAX_SYMBOL) {
      this.kind = kind;
    } else {
      throw new IllegalArgumentException("SymbolKind out of range");
    }
  }

  public int getKind() {
    return kind;
  }

  public static SymbolKind[] parseSymbolKindJSONArray(JSONArray ops) {
    int entries = ops.length();
    SymbolKind[] kinds = new SymbolKind[entries];
    for (int i = 0; i < entries; i++) {
      kinds[i] = new SymbolKind(ops.getInt(i));
    }
    return kinds;
  }
}
