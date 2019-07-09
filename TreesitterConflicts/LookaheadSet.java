import java.util.ArrayList;

/**
 * LookaheadSet represents the valid lookahead set of an item in a DFA state.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class LookaheadSet {
  /**
  * The DFA Item that this lookahead set corresponds to.
  */
  private DFA_Item associatedItem;
  /**
  * The integer identifiers of the members of the lookahead set.
  */
  private ArrayList<Integer> memberTags;

  /**
  * This constructs an empty lookahead set for the given DFA_Item.
  * @param item the item in the DFA that this lookahed set is for.
  * @since 1.0
  */
  public LookaheadSet(DFA_Item item) {
    this.associatedItem = item;
    memberTags = new ArrayList<Integer>();
  }

  /**
  * Adds a grammar symbol to the lookahead set.
  * @param memberTag the numeric identifier of the grammar symbol you want to add to the lookahead set
  * @since 1.0
  */
  public void addMember(int memberTag) {
    memberTags.add(memberTag);
  }

  /**
  * Checks if a symbol occurs in this valid lookahead set.
  * @param memberTag the numeric identifier of the symbol you want to check is in the lookahead set.
  * @return true if the symbol is in the valid lookahead set and false if it is not.
  * @since 1.0
  */
  public boolean contains(int memberTag) {
    return memberTags.contains(memberTag);
  }

  /**
  * Gets the string representation for the lookahead set.
  * @return a string specifying the symbols in the lookahead set.
  * @since 1.0
  */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Lookahead Set");
    sb.append(": [");
    for (Integer i : memberTags) {
      sb.append(i);
      sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
