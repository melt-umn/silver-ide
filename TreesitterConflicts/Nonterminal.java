public class Nonterminal extends GrammarSymbol {
  
  public Nonterminal(int tag, String id, String displayName, int grammarTag) {
    super(tag, id, displayName, grammarTag);
    type = GrammarSymbol.NONTERMINAL;
  }

  public void setFirstSet(FirstSet fstSet) {
    this.fstSet = fstSet;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(displayName);
    sb.append(' ');
    sb.append(fstSet.toString());
    sb.append('\n');
    return sb.toString();
  }
}
