import java.util.ArrayList;
import java.util.List;

/**
 * ParseState represents a row of the parse table.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class ParseState {
  /**
  * The state number within the parse table.
  */
  private int state_num;
  /**
  * The cells of the parse state.
  */
  private ArrayList<ParseCell> cells;

  /**
  * Constructs an a parse state with specified number and empty cells.
  * @param state_num the parse state number.
  * @since 1.0
  */
  public ParseState(int state_num) {
    this.state_num = state_num;
    cells = new ArrayList<ParseCell>();
  }

  /**
  * Adds a parse cell to the parse state
  * @param c the parse cell to add.
  * @since 1.0
  */
  public void addParseCell(ParseCell c) {
    cells.add(c);
  }

  /**
  * Returns the parse state number.
  * @return the parse state number.
  * @since 1.0
  */
  public int getStateNumber() {
    return state_num;
  }

  /**
  * Checks whether any of the parse cells in the parse state have a conflict.
  * @return true if any of the parse cells in the parse state have a conflict and false otherwise.
  * @since 1.0
  */
  public boolean hasConflict() {
    for (ParseCell cell : cells) {
      if (cell.hasConflict()) {
        return true;
      }
    }
    return false;
  }

  /**
  * Returns a list of of parse cells that have parse action conflicts
  * @return a list of of parse cells that have parse action conflicts
  * @since 1.0
  */
  public List<ParseCell> getConflicts() {
    List<ParseCell> conflicts = new ArrayList<ParseCell>();
    for (ParseCell cell : cells) {
      if (cell.hasConflict()) {
        conflicts.add(cell);
      }
    }
    return conflicts;
  }
}
