import java.util.ArrayList;

public class Grammar {
  private int tag;
  private String id;
  private ArrayList<Terminal> terminals;
  private ArrayList<Nonterminal> nonterminals;
  private ArrayList<Production> productions;

  public Grammar(int tag, String id) {
    this.tag = tag;
    this.id = id;
    terminals = new ArrayList<Terminal>();
    nonterminals = new ArrayList<Nonterminal>();
    productions = new ArrayList<Production>();
  }

  public void addTerminal(Terminal terminal) {
    terminals.add(terminal);
  }  

  public void addNonterminal(Nonterminal nt) {
    nonterminals.add(nt);
  }  

  public void addProduction(Production p) {
    productions.add(p);
  }  

  public int getTag() {
    return tag;
  }

  private Nonterminal getNonterminalByTag(int ntTag) {
    // tree data structure would be faster 
    for (Nonterminal nt : nonterminals) {
      if (nt.getTag() == ntTag) {
        return nt;
      }
    }
    return null;
  }

  public boolean containsNonterminal(int ntTag) {
    Nonterminal nt = getNonterminalByTag(ntTag);
    return nt != null;
  }

  public void addFirstSet(FirstSet fstSet) {
    Nonterminal nt = getNonterminalByTag(fstSet.getSymbolTag());
    if (nt != null) {
      nt.setFirstSet(fstSet);
    }
  }
  public Production getProductionByTag(int tag) {
    for (Production p : productions) {
      if (p.getTag() == tag) {
        return p;
      }
    }
    return null;
  }

  public GrammarSymbol getGrammarSymbolByTag(int tag) {
    for (Terminal t : terminals) {
      if (t.getTag() == tag) {
        return t;
      }
    }
    for (Nonterminal nt : nonterminals) {
      if (nt.getTag() == tag) {
        return nt;
      }
    }
    return null;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id: ");
    sb.append(id);
    sb.append('\n');
    sb.append("Terminals: \n");
    for (Terminal t : terminals) {
      sb.append(t.toString());
      sb.append(',');
    }
    sb.append('\n');
    sb.append("Nonterminals: \n");
    for (Nonterminal nt : nonterminals) {
      sb.append(nt.toString());
      sb.append(',');
    }
    sb.append('\n');
    sb.append("Productions: \n");
    for (Production p : productions) {
      sb.append(p.toString());
      sb.append('\n');
    }
    return sb.toString();
  }
}
