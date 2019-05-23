import java.util.ArrayList;
import java.util.List;

public class DFA_State {
  private int state_num;
  private ArrayList<DFA_Item> items;

  public DFA_State(int state_num) {
    this.state_num = state_num;
    items = new ArrayList<DFA_Item>();
  }

  public void addItem(DFA_Item i) {
    items.add(i);
  }

  public void setProductionForItems(Grammars g) {
    for (DFA_Item item : items) {
      item.setProduction(g);
    }
  }

  public List<Integer> getPossibleParents(int terminalTag) {
    List<Integer> ntParents = new ArrayList<Integer>();
    for (DFA_Item item : items) {
      if (item.isPossibleParent(terminalTag)) {
        ntParents.add(item.getProductionLHS());
      }
    }
    return ntParents;
  }
}
