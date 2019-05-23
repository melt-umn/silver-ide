import java.util.ArrayList;

public class LALR_DFA {
  private ArrayList<DFA_State> states;

  public LALR_DFA() {
    states = new ArrayList<DFA_State>();
  }

  public void addState(DFA_State s) {
    states.add(s);
  }

  public DFA_State getState(int state_num) {
      // subtract 1 since state numbers start at 1
      return states.get(state_num-1);
  }
}
