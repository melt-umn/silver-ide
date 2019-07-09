
/**
 * ParseActionReduce represents a reduce action in a parse cell.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class ParseActionReduce extends ParseAction {
  /**
  * The numeric identifier of the production that is reduced
  */
  private int productionReduceNum;

  /**
  * Constructs a new reduce action which reduces the specified production.
  * @param productionReduceNum the numeric identifier of the production being reduced in this cell.
  * @since 1.0
  */
  public ParseActionReduce(int productionReduceNum) {
    this.productionReduceNum = productionReduceNum;
    this.actionType = ParseAction.REDUCE;
  }
}
