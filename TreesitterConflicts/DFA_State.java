import java.util.ArrayList;
import java.util.List;

/**
 * DFA_State represents a state within the DFA.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class DFA_State {
  /**
  * The numeric identifier of which DFA state this represents.
  */
  private int state_num;
  /**
  * This list of DFA Items within this DFA state.
  */
  private ArrayList<DFA_Item> items;

  /**
  * Constructs a new DFA_State with a specified state number.
  * @param state_num the numeric identifier of the state.
  * @since 1.0
  */
  public DFA_State(int state_num) {
    this.state_num = state_num;
    items = new ArrayList<DFA_Item>();
  }

  /**
  * Adds an item to the DFA.
  * @param i the item that you want to add to this state.
  * @since 1.0
  */
  public void addItem(DFA_Item i) {
    items.add(i);
  }

  /**
  * Sets the productions for all the items within the state.
  * @param g the grammars these productions exist within.
  * @since 1.0
  */
  public void setProductionForItems(Grammars g) {
    for (DFA_Item item : items) {
      item.setProduction(g);
    }
  }

  /**
  * Returns the possible parent nonterminals at this state in the DFA given a specified terminal.
  * @param terminalTag the numeric identifier of the specified terminal.
  * @return a list of numeric identifiers representing the nonterminals that could be possible parents of the specified terminal if the parser is at this state in the DFA.
  * @since 1.0
  */
  public List<Integer> getPossibleParents(int terminalTag) {
    List<Integer> ntParents = new ArrayList<Integer>();
    for (DFA_Item item : items) {
      if (item.isPossibleParent(terminalTag)) {
        ntParents.add(item.getProductionLHS());
      }
    }
    return ntParents;
  }
}
