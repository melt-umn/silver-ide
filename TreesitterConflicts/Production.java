import java.util.ArrayList;

public class Production {
  private int tag;
  private String id;
  private int associatedGrammarTag;
  private int lhsNonterminalTag;
  private ArrayList<Integer> rhsSymbolTags;

  public Production(int tag, String id, int grammarTag) {
    this.tag = tag;
    this.id = id;
    this.associatedGrammarTag = grammarTag;
    rhsSymbolTags = new ArrayList<Integer>();
  }

  public int getTag() {
    return tag;
  }

  public int getGrammarTag() {
    return associatedGrammarTag;
  }

  public void setLHSNonterminalTag(int lhs) {
    this.lhsNonterminalTag = lhs;
  }

  public void addRHSSymbolTag(int rhsElem) {
    rhsSymbolTags.add(rhsElem);
  }

  public int getNumberRHSSymbols() {
    return rhsSymbolTags.size();
  }

  public int getLHSTag() {
    return lhsNonterminalTag;
  }

  public int getNextSymbol(int curPos) {
    return rhsSymbolTags.get(curPos);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(lhsNonterminalTag);
    sb.append("->");
    for (Integer tag : rhsSymbolTags) {
      sb.append(tag);
      sb.append(' ');
    }
    return sb.toString();
  }
}
