public class Terminal extends GrammarSymbol {

  public Terminal(int tag, String id, String displayName, int grammarTag) {
    super(tag, id, displayName, grammarTag);
    type = GrammarSymbol.TERMINAL;
    fstSet = new FirstSet(tag);
    fstSet.addMember(tag);
  }
}
