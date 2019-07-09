import java.util.ArrayList;

/**
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class Production {
  /**
  * The numeric identifier of the production.
  */
  private int tag;
  /**
  * The string identifier of the production.
  */
  private String id;
  /**
  * The grammar this production is from.
  */
  private int associatedGrammarTag;
  /**
  * The numeric identifier of the nonterminal on the left hand side of this
  * production.
  */
  private int lhsNonterminalTag;
  /**
  *The numeric identifier of the grammar symbols on the right hand side of this
  * production.
  */
  private ArrayList<Integer> rhsSymbolTags;

  /**
  * Constructs a new production with no left hand or right hand side.
  * @param tag the numeric identifier of the production.
  * @param id the string identifier (name) of the production.
  * @param grammarTag the grammar that this production comes from.
  * @since 1.0
  */
  public Production(int tag, String id, int grammarTag) {
    this.tag = tag;
    this.id = id;
    this.associatedGrammarTag = grammarTag;
    rhsSymbolTags = new ArrayList<Integer>();
  }

  /**
  * Returns the numeric identifier of the production.
  * @return the numeric identifier of the production.
  * @since 1.0
  */
  public int getTag() {
    return tag;
  }

  /**
  * Returns the numeric identifier of the grammar this production is from.
  * @return the numeric identifier of the grammar this production is from.
  * @since 1.0
  */
  public int getGrammarTag() {
    return associatedGrammarTag;
  }

  /**
  * Set the nonterminal on the left hand side of this produciton.
  * @param lhs the numeric identifier of the nonterminal on the LHS of this production.
  * @since 1.0
  */
  public void setLHSNonterminalTag(int lhs) {
    this.lhsNonterminalTag = lhs;
  }

  /**
  * Add a grammar symbol to the end of the right hand side of this production.
  * @param rhsElem the numeric identifier of the grammar symbol to add.
  * @since 1.0
  */
  public void addRHSSymbolTag(int rhsElem) {
    rhsSymbolTags.add(rhsElem);
  }

  /**
  * Get the number of symbols on the right hand side of this production.
  * @return the number of symbols on the right hand side of this production.
  * @since 1.0
  */
  public int getNumberRHSSymbols() {
    return rhsSymbolTags.size();
  }

  /**
  * Get the numeric identifier of the nonterminal on the left hand side of this production.
  * @return the numeric identifier of the nonterminal on the left hand side of this production.
  * @since 1.0
  */
  public int getLHSTag() {
    return lhsNonterminalTag;
  }

  /**
  * Get the next grammar symbol in the production given the specified position.
  * @param curPos the current position in the production.
  * @return the numeric identifier of the next grammar symbol on the RHS of the production.
  * @throws IndexOutOfBoundsException if the current position is the last of past the last symbol in the production.
  * @since 1.0
  */
  public int getNextSymbol(int curPos) {
    // don't need to add 1 since ArrayList indexing starts at 0
    return rhsSymbolTags.get(curPos);
  }

  /**
  * Returns the string representation of the production.
  * @return the string representation of the production.
  * @since 1.0
  */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(lhsNonterminalTag);
    sb.append("->");
    for (Integer tag : rhsSymbolTags) {
      sb.append(tag);
      sb.append(' ');
    }
    return sb.toString();
  }
}
