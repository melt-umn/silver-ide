import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
 /* TODO: construct a new method that processes the entire XML so Main does not have to know about the
    inner workings of the XML dump and only needs to call that method. That method would utilize all
    the other methods already written here.
 */
public class XMLObjectCreator {
  /**
  * Process a gramamr node in the XML and creates the associated Gramamr object.
  * @param e the XML element representing the grammar.
  * @return the grammar represented by the given XML object.
  * @since 1.0
  */
  public static Grammar processGrammarNode(Element e) {
    int tag = Integer.parseInt(e.getAttribute("tag"));
    String id = e.getAttribute("id");
    return new Grammar(tag, id);
  }

  /**
  * Process a terminal node in the XML and creates the associated Terminal object.
  * @param e the XML element representing the terminal.
  * @return the terminal represented by the given XML object.
  * @since 1.0
  */
  public static Terminal processTerminalNode(Element e) {
    e.normalize();
    int tag = Integer.parseInt(e.getAttribute("tag"));
    int grammarTag;
    try {
      grammarTag = Integer.parseInt(e.getAttribute("owner"));
    } catch (Exception exp) {
      grammarTag = -1;
    }
    String id = e.getAttribute("id");
    String displayName = e.getElementsByTagName("DisplayName").item(0).getTextContent();
    return new Terminal(tag, id, displayName, grammarTag);
  }

  /**
  * Process a nonterminal node in the XML and creates the associated Nonterminal object.
  * @param e the XML element representing the nonterminal.
  * @return the nonterminal represented by the given XML object.
  * @since 1.0
  */
  public static Nonterminal processNonterminalNode(Element e) {
    e.normalize();
    int tag = Integer.parseInt(e.getAttribute("tag"));
    int grammarTag;
    try {
      grammarTag = Integer.parseInt(e.getAttribute("owner"));
    } catch (Exception exp) {
      grammarTag = -1;
    }
    String id = e.getAttribute("id");
    String displayName = e.getElementsByTagName("DisplayName").item(0).getTextContent();
    return new Nonterminal(tag, id, displayName, grammarTag);
  }

  /**
  * Process a production node in the XML and creates the associated Production object.
  * @param e the XML element representing the production.
  * @return the production represented by the given XML object.
  * @since 1.0
  */
  public static Production processProductionNode(Element e) {
    e.normalize();
    int tag = Integer.parseInt(e.getAttribute("tag"));
    int grammarTag;
    try {
      grammarTag = Integer.parseInt(e.getAttribute("owner"));
    } catch (Exception exp) {
      grammarTag = -1;
    }
    String id = e.getAttribute("id");
    Production p = new Production(tag, id, grammarTag);
    int lhsTag = Integer.parseInt(e.getElementsByTagName("LHS").item(0).getTextContent());
    p.setLHSNonterminalTag(lhsTag);
    Node rhs = e.getElementsByTagName("RHS").item(0);
    if (rhs != null && rhs.getNodeType() == Node.ELEMENT_NODE) {
      Element rhsElem = (Element) rhs;
      Node curNode = rhsElem.getFirstChild();
      while (curNode != null) {
        if (curNode.getNodeType() == Node.ELEMENT_NODE) {
          Element curElem = (Element) curNode;
          int rhsTag = Integer.parseInt(curElem.getAttribute("ref"));
          p.addRHSSymbolTag(rhsTag);
        }
        curNode = curNode.getNextSibling();
      }
    }
    return p;
  }

  /**
  * Process a first set node in the XML and creates the associated FirstSet object.
  * @param e the XML element representing the firsts et.
  * @return the first set represented by the given XML object.
  * @since 1.0
  */
  public static FirstSet processFirstSet(Element e) {
    int ntTag = Integer.parseInt(e.getAttribute("of"));
    FirstSet fs = new FirstSet(ntTag);
    Node memberNode = e.getFirstChild();
    while (memberNode != null) {
      if (memberNode.getNodeType() == Node.ELEMENT_NODE) {
        Element member = (Element) memberNode;
        int memberTag = Integer.parseInt(member.getAttribute("ref"));
        fs.addMember(memberTag);
      }
      memberNode = memberNode.getNextSibling();
    }
    return fs;
  }

  /**
  * Process all the context sets node in the XML and creates the associated objects and add them to the grammars.
  * @param e the XML element representing the context sets.
  * @param g the grammars to add the context sets to.
  * @since 1.0
  */
  public static void processContextSets(Element e, Grammars g) {
    Node curContextSet = e.getFirstChild();
    while (curContextSet != null) {
      if (curContextSet.getNodeType() == Node.ELEMENT_NODE) {
        Element contextSetElem = (Element) curContextSet;
        String tag = contextSetElem.getTagName();
        if (tag == "First") {
          FirstSet fs = XMLObjectCreator.processFirstSet(contextSetElem);
          g.addFirstSet(fs);
        }
        // skip FirstNT not needed right now
        // skip Follow sets not needed now
        // skip Nullable not needed for this application
      }
      curContextSet = curContextSet.getNextSibling();
    }
  }

  /**
  * Process a DFA item node in the XML and creates the associated DFA_Item object.
  * @param e the XML element representing the DFA item.
  * @return the DFA item represented by the given XML object.
  * @since 1.0
  */
  public static DFA_Item processDFA_Item(Element e) {
    int productionTag = Integer.parseInt(e.getAttribute("production"));
    int curPosition = Integer.parseInt(e.getAttribute("marker"));
    DFA_Item item = new DFA_Item(productionTag, curPosition);
    LookaheadSet laSet = new LookaheadSet(item);
    Node curLookaheadMember = e.getFirstChild();
    while (curLookaheadMember != null) {
      if (curLookaheadMember.getNodeType() == Node.ELEMENT_NODE) {
        Element lookaheadMemberElem = (Element) curLookaheadMember;
        int ref = Integer.parseInt(lookaheadMemberElem.getAttribute("ref"));
        laSet.addMember(ref);
      }
      curLookaheadMember = curLookaheadMember.getNextSibling();
    }
    item.setLookaheadSet(laSet);
    return item;
  }

  /**
  * Process a DFA state node in the XML and creates the associated DFA_State object.
  * @param e the XML element representing the DFA state.
  * @return the DFa state represented by the given XML object.
  * @since 1.0
  */
  public static DFA_State processDFA_State(Element e) {
    int state_num = Integer.parseInt(e.getAttribute("id"));
    DFA_State state = new DFA_State(state_num);
    Node curItem = e.getFirstChild();
    while (curItem != null) {
      if (curItem.getNodeType() == Node.ELEMENT_NODE) {
        Element curItemElem = (Element) curItem;
        String tag = curItemElem.getTagName();
        if (tag == "Item") {
          DFA_Item item = XMLObjectCreator.processDFA_Item(curItemElem);
          state.addItem(item);
        } else if (tag == "Transition") {
          // do nothing for now
        }
      }
      curItem = curItem.getNextSibling();
    }
    return state;
  }

  /**
  * Process an LALR DFA node in the XML and creates the associated LALR DFA object.
  * @param e the XML element representing the LALR DFA.
  * @return the LALR DFA represented by the given XML object.
  * @since 1.0
  */
  public static LALR_DFA processLALR_DFA(Element e) {
    LALR_DFA dfa = new LALR_DFA();
    Node curState = e.getFirstChild();
    while (curState != null) {
      if (curState.getNodeType() == Node.ELEMENT_NODE) {
        Element curStateElem = (Element) curState;
        DFA_State state = XMLObjectCreator.processDFA_State(curStateElem);
        dfa.addState(state);
      }
      curState = curState.getNextSibling();
    }
    return dfa;
  }

  /**
  * Process a parse cell node in the XML and creates the associated ParseCell object.
  * @param e the XML element representing the parse cell.
  * @return the parse cell represented by the given XML object.
  * @since 1.0
  */
  public static ParseCell processParseCell(Element e) {
    int terminalTag = Integer.parseInt(e.getAttribute("id"));
    ParseCell cell = new ParseCell(terminalTag);
    Node curAction = e.getFirstChild();
    while (curAction != null) {
      if (curAction.getNodeType() == Node.ELEMENT_NODE) {
        Element curActionElem = (Element) curAction;
        String tag = curActionElem.getTagName();
        if (tag == "Shift") {
          String dest = curActionElem.getAttribute("dest");
          // strip off the tr
          int state = Integer.parseInt(dest.substring(2));
          ParseActionShift act = new ParseActionShift(state);
          cell.addParseAction(act);

        } else if (tag == "Reduce") {
          int prodTag = Integer.parseInt(curActionElem.getAttribute("prod"));
          ParseActionReduce act = new ParseActionReduce(prodTag);
          cell.addParseAction(act);
        }
      }
      curAction = curAction.getNextSibling();
    }
    return cell;
  }

  /**
  * Process a parse state node in the XML and creates the associated ParseState object.
  * @param e the XML element representing the parse state.
  * @return the parse state represented by the given XML object.
  * @since 1.0
  */
  public static ParseState processParseState(Element e) {
    int state_num = Integer.parseInt(e.getAttribute("id"));
    ParseState state = new ParseState(state_num);
    Node curCell = e.getFirstChild();
    while (curCell != null) {
      if (curCell.getNodeType() == Node.ELEMENT_NODE) {
        Element curCellElem = (Element) curCell;
        String tag = curCellElem.getTagName();
        if (tag == "Layout") {
          // do nothing for now
        } else if (tag == "ParseCell") {
          ParseCell cell = XMLObjectCreator.processParseCell(curCellElem);
          cell.setParseStateNumber(state_num);
          state.addParseCell(cell);
        } else if (tag == "GotoCell") {
          // do nothing for now
        }
      }
      curCell = curCell.getNextSibling();
    }
    return state;
  }

  /**
  * Process an LR parse table node in the XML and creates the associated LRParseTable object.
  * @param e the XML element representing the LR Parse Table.
  * @return the LR Parse Table represented by the given XML object.
  * @since 1.0
  */
  public static LRParseTable processLRParseTable(Element e) {
    LRParseTable table = new LRParseTable();
    Node curState = e.getFirstChild();
    while (curState != null) {
      if (curState.getNodeType() == Node.ELEMENT_NODE) {
        Element curStateElem = (Element) curState;
        ParseState state = XMLObjectCreator.processParseState(curStateElem);
        table.addState(state);
      }
      curState = curState.getNextSibling();
    }
    return table;
  }
}
