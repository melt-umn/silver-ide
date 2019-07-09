
/**
 * ParseAction represents an action that can be taken in a parse cell either a shift or a reduce.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
abstract public class ParseAction {
  /**
  * A constant representing that the parse action is a shift.
  */
  public static final int SHIFT = 1;
  /**
  * A constant representing that the parse action is a reduce.
  */
  public static final int REDUCE = 2;
  /**
  * The type of parse action it is either a SHIFT OR REDUCE
  */
  protected int actionType;

  /**
  * Returns an integer representing whether the action is a shift or a reduce.
  * @return an integer representing whether the action is a shift or a reduce.
  * @since 1.0
  */
  public int getActionType() {
    return actionType;
  }

}
