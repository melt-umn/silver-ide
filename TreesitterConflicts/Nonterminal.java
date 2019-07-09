
/**
 * Nonterminal represents a nonterminal in a formal grammar.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class Nonterminal extends GrammarSymbol {

  /**
  * Constructs a new nonterminal symbol
  * @param tag the numeric identifier of the nonterminal.
  * @param id the string identifier (name) of the nonterminal.
  * @param displayName the name used to display this nontermianl.
  * @param grammarTag the grammar that this nonterminal comes from.
  * @since 1.0
  */
  public Nonterminal(int tag, String id, String displayName, int grammarTag) {
    super(tag, id, displayName, grammarTag);
    type = GrammarSymbol.NONTERMINAL;
  }

  /**
  * Sets the first set for the nonterminal.
  * @param fstSet the first set for this nonterminal.
  * @since 1.0 
  */
  public void setFirstSet(FirstSet fstSet) {
    this.fstSet = fstSet;
  }

  /**
  * Returns the string representation of the nonterminal and its first set
  * @return the string representation of the nonterminal and its first set
  * @since 1.0
  */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(displayName);
    sb.append(' ');
    sb.append(fstSet.toString());
    sb.append('\n');
    return sb.toString();
  }
}
