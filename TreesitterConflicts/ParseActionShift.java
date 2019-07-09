
/**
 * ParseActionShift represents a shift action in a parse cell.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class ParseActionShift extends ParseAction {
  /**
  * The numeric identifier of the parse state to shift to.
  */
  private int shiftStateNum;

  /**
  * Constructs a new shift action which shifts to the specified state number.
  * @param shiftStateNum the state number to shift to.
  * @since 1.0
  */
  public ParseActionShift(int shiftStateNum) {
    this.shiftStateNum = shiftStateNum;
    this.actionType = ParseAction.SHIFT;
  }
}
