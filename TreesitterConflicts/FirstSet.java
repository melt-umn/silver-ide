import java.util.ArrayList;

public class FirstSet {
  private int symbolTag;
  private ArrayList<Integer> memberTags;

  public FirstSet(int symbolTag) {
    this.symbolTag = symbolTag;;
    memberTags = new ArrayList<Integer>();
  }

  public void addMember(int memberTag) {
    memberTags.add(memberTag);
  }

  public int getSymbolTag() {
    return symbolTag;
  }

  public boolean contains(int memberTag) {
    return memberTags.contains(memberTag);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Fist Set for ");
    sb.append(symbolTag);
    sb.append(": [");
    for (Integer i : memberTags) {
      sb.append(i);
      sb.append(',');
    }
    sb.append(']');
    return sb.toString();
  }
}
