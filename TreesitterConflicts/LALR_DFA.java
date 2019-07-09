import java.util.ArrayList;

/**
 * LALR_DFA represents an entire DFA which is effectively a collection of states for this application.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class LALR_DFA {
  /**
  * The states of the DFA.
  */
  private ArrayList<DFA_State> states;

  /**
  * Constructs an empty DFA with no states.
  * @since 1.0
  */
  public LALR_DFA() {
    states = new ArrayList<DFA_State>();
  }

  /**
  * Adds a state to the DFA.
  * @param s the state to add to the DFA.
  * @since 1.0
  */
  public void addState(DFA_State s) {
    states.add(s);
  }

  /**
  * Get the DFA state with the specified state number.
  * @return the DFA state with the specified state number.
  * @since 1.0
  */
  public DFA_State getState(int state_num) {
    // subtract 1 since state numbers start at 1
    // but we index arrays starting at 0.
    return states.get(state_num-1);
  }
}
