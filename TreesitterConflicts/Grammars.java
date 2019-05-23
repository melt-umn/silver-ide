import java.util.ArrayList;

public class Grammars {
  public ArrayList<Grammar> grammars;

  public Grammars() {
    grammars = new ArrayList<Grammar>();
  }

  public void addGrammar(Grammar g) {
    grammars.add(g);
  }
  
  public void addTerminal(Terminal t) {
    int grammarTag = t.getGrammarTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).getTag() == grammarTag) {
        grammars.get(i).addTerminal(t);
        return;
      }
    }
  }

  public void addNonterminal(Nonterminal nt) {
    int grammarTag = nt.getGrammarTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).getTag() == grammarTag) {
        grammars.get(i).addNonterminal(nt);
        return;
      }
    }
  }

  public void addProduction(Production p) {
    int grammarTag = p.getGrammarTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).getTag() == grammarTag) {
        grammars.get(i).addProduction(p);
        return;
      }
    }
  }

  public void addFirstSet(FirstSet fstSet) {
    int ntTag = fstSet.getSymbolTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).containsNonterminal(ntTag)) {
        grammars.get(i).addFirstSet(fstSet);
      }
    }
  }

  public Production getProductionByTag(int prodTag) {
    for (int i = 0; i < grammars.size(); i++) {
      Production p = grammars.get(i).getProductionByTag(prodTag);
      if (p != null) {
        return p;
      }
    }
    return null;
  }

  public GrammarSymbol getGrammarSymbolByTag(int tag) {
    for (int i = 0; i < grammars.size(); i++) {
      GrammarSymbol sym = grammars.get(i).getGrammarSymbolByTag(tag);
      if (sym != null) {
        return sym;
      }
    }
    return null;
  }

  public String getDisplayNameByTag(int tag) {
    return getGrammarSymbolByTag(tag).getDisplayName();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Grammar g : grammars) {
      sb.append(g.toString());
    }
    return sb.toString();
  }
}
