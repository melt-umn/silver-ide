import java.util.ArrayList;

/**
 * FirstSet represents the first set of terminals and nonterminals
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class FirstSet {
  /**
  * The numeric identifier for the symbol (terminal or nonterminal) that this
  * is the first set of.
  */
  private int symbolTag;
  /**
  * The numeric identifier for the terminals that are members of this first set.
  */
  private ArrayList<Integer> memberTags;

  /**
  * This constructs an empty first set for the grammar symbol with the specified identifier.
  * @param symbolTag the numeric identifier of the grammar symbol that has this first set.
  * @since 1.0
  */
  public FirstSet(int symbolTag) {
    this.symbolTag = symbolTag;;
    memberTags = new ArrayList<Integer>();
  }

  /**
  * Adds a grammar symbol to the first set.
  * @param memberTag the numeric identifier of the grammar symbol you want to add to the first set
  * @since 1.0
  */
  public void addMember(int memberTag) {
    memberTags.add(memberTag);
  }

  /**
  * Get the symbol that this first set is for.
  * @return the numeric identifier of the symbol this first set is for.
  * @since 1.0
  */
  public int getSymbolTag() {
    return symbolTag;
  }

  /**
  * Checks if a symbol occurs in this first set.
  * @param memberTag the numeric identifier of the symbol you want to check is in the first set.
  * @return true if the symbol is in the first set and false if it is not.
  * @since 1.0
  */
  public boolean contains(int memberTag) {
    return memberTags.contains(memberTag);
  }

  /**
  * Gets the string representation for the first set.
  * @return a string specifying the symbol this first set is for and the symbols in the first set.
  * @since 1.0
  */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Fist Set for ");
    sb.append(symbolTag);
    sb.append(": [");
    for (Integer i : memberTags) {
      sb.append(i);
      sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
