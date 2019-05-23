import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLObjectCreator {
  public static Grammar processGrammarNode(Element e) {
    int tag = Integer.parseInt(e.getAttribute("tag"));
    String id = e.getAttribute("id");
    return new Grammar(tag, id);
  }

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
        // skip FirstNT not needed rn
        // skip Follow sets not needed rn
        // skip Nullable not needed for this application
      }
      curContextSet = curContextSet.getNextSibling();
    }
  }

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
