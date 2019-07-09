import java.util.ArrayList;

/**
 * Grammars represents a collection of grammars.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class Grammars {
  /**
  * The collection of grammars.
  */
  public ArrayList<Grammar> grammars;

  /**
  * Constructs an empty collection of grammars.
  * @since 1.0
  */
  public Grammars() {
    grammars = new ArrayList<Grammar>();
  }

  /**
  * Adds a grammar to the collection of grammars.
  * @param g The grammar to add.
  * @since 1.0
  */
  public void addGrammar(Grammar g) {
    grammars.add(g);
  }

  /**
  * Adds a terminal to the appropriate grammar in the collection of grammars.
  * @param t the terminal to add.
  * @since 1.0
  */
  public void addTerminal(Terminal t) {
    int grammarTag = t.getGrammarTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).getTag() == grammarTag) {
        grammars.get(i).addTerminal(t);
        return;
      }
    }
  }

  /**
  * Adds a nonterminal to the appropriate grammar in the collection of grammars.
  * @param nt the nonterminal to add.
  * @since 1.0
  */
  public void addNonterminal(Nonterminal nt) {
    int grammarTag = nt.getGrammarTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).getTag() == grammarTag) {
        grammars.get(i).addNonterminal(nt);
        return;
      }
    }
  }

  /**
  * Adds a production to the appropriate grammar in the collection of grammars.
  * @param p the production to add.
  * @since 1.0
  */
  public void addProduction(Production p) {
    int grammarTag = p.getGrammarTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).getTag() == grammarTag) {
        grammars.get(i).addProduction(p);
        return;
      }
    }
  }

  /**
  * Adds a first set to the appropriate nonterminal in the appropriate grammar.
  * @param fstSet the first set to add.
  * @since 1.0
  */
  public void addFirstSet(FirstSet fstSet) {
    int ntTag = fstSet.getSymbolTag();
    for (int i = 0; i < grammars.size(); i++) {
      if (grammars.get(i).containsNonterminal(ntTag)) {
        grammars.get(i).addFirstSet(fstSet);
      }
    }
  }

  /**
  * Returns the production with the specified numeric identifier or null if it is not in the grammars.
  * @param prodTag the numeric identifier of the production.
  * @return the production with the specified numeric identifier or null if it is not in the grammars.
  * @since 1.0
  */
  public Production getProductionByTag(int prodTag) {
    for (int i = 0; i < grammars.size(); i++) {
      Production p = grammars.get(i).getProductionByTag(prodTag);
      if (p != null) {
        return p;
      }
    }
    return null;
  }

  /**
  * Returns the grammar symbol with the specified numeric identifier or null if it is not in the grammars.
  * @param tag the numeric identifier of the grammar symbol (terminal or nonterminal).
  * @return the grammar symbol with the specified numeric identifier or null if it is not in the grammars.
  * @since 1.0
  */
  public GrammarSymbol getGrammarSymbolByTag(int tag) {
    for (int i = 0; i < grammars.size(); i++) {
      GrammarSymbol sym = grammars.get(i).getGrammarSymbolByTag(tag);
      if (sym != null) {
        return sym;
      }
    }
    return null;
  }

  /**
  * Returns the display name of the gramamr symbol with the specified numeric identifier.
  * @param tag the numeric identifier of the grammar symbol (terminal or nonterminal).
  * @return the display name of the gramamr symbol with the specified numeric identifier.
  * @throws IllegalArgumentException if the tag is not in the collection of grammars.
  * @since 1.0
  */
  public String getDisplayNameByTag(int tag) {
    GrammarSymbol sym = getGrammarSymbolByTag(tag);
    if (sym == null) {
      throw IllegalArgumentException("Grammar Symbol with tag: " + tag + " not found in this collection of grammars");
    } else {
      return sym.getDisplayName();
    }
  }

  /**
  * Gets the string representation for the collection of grammars.
  * @return a string concatenating the string representation of each of the grammars.
  * @since 1.0
  */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Grammar g : grammars) {
      sb.append(g.toString());
    }
    return sb.toString();
  }
}
