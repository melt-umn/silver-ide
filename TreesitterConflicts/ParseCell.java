import java.util.ArrayList;

/**
 * ParseCell represents a cell in the parse table
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class ParseCell {
  /**
  * The numeric identifier for the terminal in this parse cell
  */
  private int terminalTag;
  /**
  * The parse state this cell is in
  */
  private int state_num;
  /**
  * The actions that should be performed in this parse cell
  */
  private ArrayList<ParseAction> actions;

  /**
  * Constructs a new parse cell with the specified termianl as the token of lookahead and no parse actions.
  * @param terminalTag the numeric identifier of the terminal that is the token of lookahead.
  * @since 1.0
  */
  public ParseCell(int terminalTag) {
    this.terminalTag = terminalTag;
    actions = new ArrayList<ParseAction>();
  }

  /**
  * Constructs a new parse cell with the specified termianl as the token of lookahead and specified parse action.
  * @param terminalTag the numeric identifier of the terminal that is the token of lookahead.
  * @param action the parse action that should occur in this cell.
  * @since 1.0
  */
  public ParseCell(int terminalTag, ParseAction action) {
    this.terminalTag = terminalTag;
    actions = new ArrayList<ParseAction>();
    actions.add(action);
  }

  /**
  * Add a parse action to the parse cell.
  * @param action the parse action to add.
  * @since 1.0
  */
  public void addParseAction(ParseAction action) {
    actions.add(action);
  }

  /**
  * Set the parse state this parse cell occurs in.
  * @param num the parse state number.
  * @since 1.0
  */
  public void setParseStateNumber(int num) {
    state_num = num;
  }

  /**
  * Returns the parse state number this parse cell is in.
  * @return the parse state number this parse cell is in.
  * @since 1.0
  */
  public int getParseStateNumber() {
    return state_num;
  }

  /**
  * Returns the numeric identifier of the terminal that is the token of lookahead.
  * @return the numeric identifier of the terminal that is the token of lookahead.
  * @since 1.0
  */
  public int getTerminalTag() {
    return terminalTag;
  }

  /**
  * Returns whether or not there are multiple parse actions in this state.
  * @return whether or not there are multiple parse actions in this state indicating there is a parse conflict.
  * @since 1.0
  */
  public boolean hasConflict() {
    return actions.size() > 1;
  }
}
