import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an LR parse table.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class LRParseTable {
  /**
  * The states of the parse table.
  */
  private ArrayList<ParseState> states;

  /**
  * Constructs an empty parse table
  * @since 1.0
  */
  public LRParseTable() {
    states = new ArrayList<ParseState>();
  }

  /**
  * Adds a parse state to the parse table
  * @param s the parse state to add to the table.
  * @since 1.0
  */
  public void addState(ParseState s) {
    states.add(s);
  }

  /**
  * Returns the list of states where a parse table conflict occurs.
  * @return the list of states where a parse table conflict occurs.
  * @since 1.0
  */
  public List<Integer> getStatesWithConflict() {
    List<Integer> conflicts = new ArrayList<Integer>();
    for (ParseState state : states) {
      if (state.hasConflict()) {
        conflicts.add(state.getStateNumber());
      }
    }
    return conflicts;
  }

  /**
  * Returns the list of parse cells where there exists a parse action conflict.
  * @return the list of parse cells where there exists a parse action conflict.
  * @since 1.0
  */
  public List<ParseCell> getConflicts() {
    List<ParseCell> conflicts = new ArrayList<ParseCell>();
    for (ParseState state : states) {
      List<ParseCell> conflictsForState = state.getConflicts();
      conflicts.addAll(conflictsForState);
    }
    return conflicts;
  }
}
