
public class ParseActionShift extends ParseAction {
  private int shiftStateNum;

  public ParseActionShift(int shiftStateNum) {
    this.shiftStateNum = shiftStateNum;
    this.actionType = ParseAction.SHIFT;
  }
}
