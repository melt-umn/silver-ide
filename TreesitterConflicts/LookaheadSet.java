import java.util.ArrayList;

public class LookaheadSet {
  private DFA_Item associatedItem;
  private ArrayList<Integer> memberTags;

  public LookaheadSet(DFA_Item item) {
    this.associatedItem = item;
    memberTags = new ArrayList<Integer>();
  }

  public void addMember(int memberTag) {
    memberTags.add(memberTag);
  }

  public boolean contains(int memberTag) {
    return memberTags.contains(memberTag);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Lookahead Set");
    sb.append(": [");
    for (Integer i : memberTags) {
      sb.append(i);
      sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
