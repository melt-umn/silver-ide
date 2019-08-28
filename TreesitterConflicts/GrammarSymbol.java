
/**
 * GrammarSymbol represents a symbol in the grammar which is either a terminal or a nonterminal.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public abstract class GrammarSymbol {
  /**
  * A constant representing that the grammar symbol is a terminal.
  */
  public static final int TERMINAL = 1;
  /**
  * A constant representing that the grammar symbol is a nonterminal.
  */
  public static final int NONTERMINAL = 2;
  /**
  * The numeric identifier of the grammar symbol.
  */
  private int tag;
  /**
  * The string identifier of the grammar symbol.
  */
  private String id;
  /**
  * The name to be displayed for the grammar symbol.
  */
  protected String displayName;
  /**
  * The grammar this grammar symbol is from.
  */
  private int associatedGrammarTag;
  /**
  * The type of grammar symbol it is either a TERMINAL OR NONTERMINAL.
  */
  protected int type;
  /**
  * The first set of the the grammar symbol/
  */
  protected FirstSet fstSet;

  /**
  * Constructs a new grammar symbol
  * @param tag the numeric identifier of the grammar symbol.
  * @param id the string identifier (name) of the grammar symbol.
  * @param displayName the name used to display this grammar symbol.
  * @param grammarTag the grammar that this grammar symbol comes from.
  * @since 1.0
  */
  public GrammarSymbol(int tag, String id, String displayName, int grammarTag) {
    this.tag = tag;
    this.id = id;
    this.displayName = displayName;
    this.associatedGrammarTag = grammarTag;
  }

  /**
  * Returns the numeric identifier of the grammar symbol.
  * @return the numeric identifier of the grammar symbol.
  * @since 1.0
  */
  public int getTag() {
    return tag;
  }

  /**
  * Returns the first set of the grammar symbol.
  * @return the first set of the grammar symbol.
  * @since 1.0
  */
  public FirstSet getFirstSet() {
    return fstSet;
  }

  /**
  * Returns the numeric identifier of the grammar this symbol is from.
  * @return the numeric identifier of the grammar this symbol is from.
  * @since 1.0
  */
  public int getGrammarTag() {
    return associatedGrammarTag;
  }

  /**
  * Returns the display name of the grammar symbol.
  * @return the display name of the grammar symbol.
  * @since 1.0
  */
  public String getDisplayName() {
    return displayName;
  }

  /**
  * Returns the string representation of the grammar symbol.
  * @return the display name of the grammar symbol.
  * @since 1.0
  */
  public String toString() {
    return displayName;
  }

  /**
  * Get the type of grammar symbol which is either a terminal or nontermial.
  * @return an integer representing whether this grammar symbol is a terminal or nonterminal.
  * @since 1.0
  */
  public int getType() {
    return type;
  }
}
