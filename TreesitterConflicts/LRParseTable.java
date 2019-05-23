import java.util.ArrayList;
import java.util.List;
public class LRParseTable {
  private ArrayList<ParseState> states;

  public LRParseTable() {
    states = new ArrayList<ParseState>();
  }

  public void addState(ParseState s) {
    states.add(s);
  }

  public List<Integer> getStatesWithConflict() {
    List<Integer> conflicts = new ArrayList<Integer>();
    for (ParseState state : states) {
      if (state.hasConflict()) {
        conflicts.add(state.getStateNumber());
      }
    }
    return conflicts;
  }

  public List<ParseCell> getConflicts() {
    List<ParseCell> conflicts = new ArrayList<ParseCell>();
    for (ParseState state : states) {
      List<ParseCell> conflictsForState = state.getConflicts();
      conflicts.addAll(conflictsForState);
    }
    return conflicts;
  }
}
