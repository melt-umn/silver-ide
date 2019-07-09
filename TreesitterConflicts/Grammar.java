import java.util.ArrayList;

/**
 * Grammar represents a formal grammar with nontermianls, termianls and productions.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class Grammar {
  /**
  * The numeric identifier of the grammar.
  */
  private int tag;
  /**
  * The string identifier of the grammar.
  */
  private String id;
  /**
  * The terminals in the grammar.
  */
  private ArrayList<Terminal> terminals;
  /**
  * The nonterminals in the grammar.
  */
  private ArrayList<Nonterminal> nonterminals;
  /**
  * The productions in the grammar
  */
  private ArrayList<Production> productions;

  /**
  * This constructs an empty grammar with a numeric identifier and a name.
  * @param tag the numeric identifier of the grammar.
  * @param id the name of the grammar.
  * @since 1.0
  */
  public Grammar(int tag, String id) {
    this.tag = tag;
    this.id = id;
    terminals = new ArrayList<Terminal>();
    nonterminals = new ArrayList<Nonterminal>();
    productions = new ArrayList<Production>();
  }

  /**
  * Adds a terminal to the grammar.
  * @param terminal the terminal to add to the grammar.
  * @since 1.0
  */
  public void addTerminal(Terminal terminal) {
    terminals.add(terminal);
  }

  /**
  * Adds a nonterminal to the grammar.
  * @param nt the nonterminal to add to the grammar.
  * @since 1.0
  */
  public void addNonterminal(Nonterminal nt) {
    nonterminals.add(nt);
  }

  /**
  * Adds a production to the grammar.
  * @param p the production to add to the grammar.
  * @since 1.0
  */
  public void addProduction(Production p) {
    productions.add(p);
  }

  /**
  * Returns the numeric identifier of the grammar.
  * @return the numeric identifier of the grammar.
  * @since 1.0
  */
  public int getTag() {
    return tag;
  }

  /**
  * Returns the nonterminal with the specified numeric identifier or null if it is not in this grammar.
  * @param ntTag the numeric identifier of the nonterminal.
  * @return the nonterminal with the specified numeric identifier or null if it is not in this grammar.
  * @since 1.0
  */
  private Nonterminal getNonterminalByTag(int ntTag) {
    // tree data structure would be faster
    for (Nonterminal nt : nonterminals) {
      if (nt.getTag() == ntTag) {
        return nt;
      }
    }
    return null;
  }

  /**
  * Checks whether or not the grammar contains a nonterminal with the specified numeric identifier.
  * @param ntTag the numeric identifier of the nonterminal
  * @return true if the nonterminal is in the grammar and false if it is not.
  * @since 1.0
  */
  public boolean containsNonterminal(int ntTag) {
    Nonterminal nt = getNonterminalByTag(ntTag);
    return nt != null;
  }

  /**
  * Adds the first set to the appropriate nontermianl in the grammar or does nothing if the nonterminal is not in the grammar.
  * @param fstSet the first set to add.
  */
  public void addFirstSet(FirstSet fstSet) {
    Nonterminal nt = getNonterminalByTag(fstSet.getSymbolTag());
    if (nt != null) {
      nt.setFirstSet(fstSet);
    }
  }

  /**
  * Returns the production with the specified numeric identifier or null if it is not in this grammar.
  * @param tag the numeric identifier of the production.
  * @return the production with the specified numeric identifier or null if it is not in this grammar.
  * @since 1.0
  */
  public Production getProductionByTag(int tag) {
    for (Production p : productions) {
      if (p.getTag() == tag) {
        return p;
      }
    }
    return null;
  }

  /**
  * Returns the grammar symbol with the specified numeric identifier or null if it is not in this grammar.
  * @param tag the numeric identifier of the grammar symbol (terminal or nonterminal).
  * @return the grammar symbol with the specified numeric identifier or null if it is not in this grammar.
  * @since 1.0
  */
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

  /**
  * Gets the string representation for the grammar.
  * @return a string specifying the grammar name and all the terminals, nontermianls and productions string representations.
  * @since 1.0
  */
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
