
/**
 * Terminal represnts a terminal in a formal grammar.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class Terminal extends GrammarSymbol {

  /**
  * Constructs a new terminal symbol
  * @param tag the numeric identifier of the erminal.
  * @param id the string identifier (name) of the terminal.
  * @param displayName the name used to display this termianl.
  * @param grammarTag the grammar that this terminal comes from.
  * @since 1.0
  */
  public Terminal(int tag, String id, String displayName, int grammarTag) {
    super(tag, id, displayName, grammarTag);
    type = GrammarSymbol.TERMINAL;
    // the only element in the first set of a terminal is itself.
    fstSet = new FirstSet(tag);
    fstSet.addMember(tag);
  }
}
