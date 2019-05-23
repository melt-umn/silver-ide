public abstract class GrammarSymbol {
  public static final int TERMINAL = 1;
  public static final int NONTERMINAL = 2;
  private int tag;
  private String id;
  protected String displayName;
  private int associatedGrammarTag;
  protected int type;
  protected FirstSet fstSet;

  public GrammarSymbol(int tag, String id, String displayName, int grammarTag) {
    this.tag = tag;
    this.id = id;
    this.displayName = displayName;
    this.associatedGrammarTag = grammarTag;
  }

  public int getTag() {
    return tag;
  }

  public FirstSet getFirstSet() {
    return fstSet;
  }
  public int getGrammarTag() {
    return associatedGrammarTag;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String toString() {
    return displayName;
  }

  public int getType() {
    return type;
  }
}

