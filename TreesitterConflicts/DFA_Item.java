
/**
 * DFA_Item represents a single item within a DFA state
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class DFA_Item {
  /**
  * The numeric identiifer of the production that this DFA Item belongs to.
  */
  private int productionTag;
  /**
  * The actual production associated with this DFA Item which is
  * null until setProduction is called.
  */
  private Production production;
  /**
  * The grammars included in the parser specification which is null until
  * setProduction is called.
  */
  private Grammars grammars;
  /**
  * The current position within the production this DFA Item is in
  * (where the dot is).
  */
  private int curPosition;
  /**
  * The lookahead set associated with this DFA Item.
  */
  private LookaheadSet laSet;

  /**
  * This constructs a DFA Item from the numeric identifier of the production
  * and where in the production this specific item is at.
  * @param productionTag the numeric identifier of the production for this DFA item.
  * @param curPosition where in the production the DFA item is at (where the dot is).
  * @since 1.0
  */
  public DFA_Item(int productionTag, int curPosition) {
    this.productionTag = productionTag;
    this.curPosition = curPosition;
    // set the actual production from the productionTag lazily - only when needed.
    this.production = null;
  }

  /**
  * Sets the valid lookahead set of the DFA Item to the specified lookahead set
  * @param l the valid lookahead set for this DFA item.
  * @since 1.0
  */
  public void setLookaheadSet(LookaheadSet l) {
    laSet = l;
  }

  /**
  * Sets the production object for this DFA Item.
  * @param g the grammars this production occurs in
  * @since 1.0
  */
  public void setProduction(Grammars g) {
    grammars = g;
    if (production == null) {
      production = g.getProductionByTag(productionTag);
    }
  }

  /**
  * Returns the numeric identifier of the left hand side of the production at this DFA Item.
  * @return the numeric identifier of the left hand side of the production at this DFA Item.
  * @since 1.0
  */
  public int getProductionLHS() {
    return production.getLHSTag();
  }

  /**
  * Returns a boolean indicating whether for a specified terminal the nonterminal
  * on the left hand side of this production could be the possible parent.
  * @param terminalTag the numeric identifier of the terminal that we are considering.
  * @return whether or not the nonterminal on the left hand side of this production can be a possible parent of the terminal passed in.
  * @since 1.0
  */
  public boolean isPossibleParent(int terminalTag) {
    // if (reduce action) its a possible parent if its in the valid lookahead set
    if (production.getNumberRHSSymbols() == curPosition) {
      return laSet.contains(terminalTag);
    }
    // if (in progress shift action) its a possible parent if the
    if (curPosition != 0) {
      int tag = production.getNextSymbol(curPosition);
      GrammarSymbol gs = grammars.getGrammarSymbolByTag(tag);
      FirstSet fstSet = gs.getFirstSet();
      return fstSet.contains(terminalTag);
    }
    return false;
  }
}
