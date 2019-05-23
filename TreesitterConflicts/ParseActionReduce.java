
public class ParseActionReduce extends ParseAction {
  private int productionReduceNum;

  public ParseActionReduce(int productionReduceNum) {
    this.productionReduceNum = productionReduceNum;
    this.actionType = ParseAction.REDUCE;
  }
}
