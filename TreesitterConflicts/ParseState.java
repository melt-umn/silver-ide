import java.util.ArrayList;
import java.util.List;

public class ParseState {
  private int state_num;
  private ArrayList<ParseCell> cells;

  public ParseState(int state_num) {
    this.state_num = state_num;
    cells = new ArrayList<ParseCell>();
  }
  
  public void addParseCell(ParseCell c) {
    cells.add(c);
  }

  public int getStateNumber() {
    return state_num;
  }

  public boolean hasConflict() {
    for (ParseCell cell : cells) {
      if (cell.hasConflict()) {
        return true;
      }
    }
    return false;
  }

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
