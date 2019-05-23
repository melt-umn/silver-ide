
public class DFA_Item {
  private int productionTag;
  private Production production;
  private Grammars grammars;
  private int curPosition;
  private LookaheadSet laSet;

  public DFA_Item(int productionTag, int curPosition) {
    this.productionTag = productionTag;
    this.curPosition = curPosition;
    this.production = null;
  }

  public void setLookaheadSet(LookaheadSet l) {
    laSet = l;
  }

  public void setProduction(Grammars g) {
    grammars = g;
    if (production == null) {
      production = g.getProductionByTag(productionTag);
    } 
  }

  public int getProductionLHS() {
    return production.getLHSTag();
  }

  public boolean isPossibleParent(int terminalTag) {
    // reduce action
    if (production.getNumberRHSSymbols() == curPosition) {
      return laSet.contains(terminalTag);
    }
    // in progress shift action
    if (curPosition != 0) {
      int tag = production.getNextSymbol(curPosition);
      GrammarSymbol gs = grammars.getGrammarSymbolByTag(tag);
      FirstSet fstSet = gs.getFirstSet();
      return fstSet.contains(terminalTag);
    }
    return false;
  }
}
