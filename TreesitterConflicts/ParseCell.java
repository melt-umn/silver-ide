import java.util.ArrayList;

public class ParseCell {
  private int terminalTag;
  private int state_num; 
  private ArrayList<ParseAction> actions;

  public ParseCell(int terminalTag) {
    this.terminalTag = terminalTag;
    actions = new ArrayList<ParseAction>();
  }

  public ParseCell(int terminalTag, ParseAction action) {
    this.terminalTag = terminalTag;
    actions = new ArrayList<ParseAction>();
    actions.add(action);
  }

  public void addParseAction(ParseAction action) {
    actions.add(action);
  }
  
  public void setParseStateNumber(int num) {
    state_num = num;
  }

  public int getParseStateNumber() {
    return state_num;
  }

  public int getTerminalTag() {
    return terminalTag;
  }

  public boolean hasConflict() {
    return actions.size() > 1;
  }
}
