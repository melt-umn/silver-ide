
abstract public class ParseAction {
  public static final int SHIFT = 1;
  public static final int REDUCE = 2;
  protected int actionType;
  
  public int getActionType() {
    return actionType;
  }

}
