import java.util.ArrayList;
import java.util.List;
import java.io.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Main {

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: java Main grammar.js xmlDumpFile");
      return;
    }
    try {
      // read in the new grammar.js file
      File grammarJsNoConflicts = new File(args[0]);
      grammarJsNoConflicts.renameTo(new File("grammarWithoutConflicts.js"));

      File xmlDump = new File(args[1]);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlDump);
      doc.getDocumentElement().normalize();

      Grammars grammars = new Grammars();
      LALR_DFA dfa = new LALR_DFA();
      LRParseTable table = new LRParseTable();
      Node curNode = doc.getDocumentElement().getFirstChild();
      while (curNode != null) {
        if (curNode.getNodeType() == Node.ELEMENT_NODE) {
          Element curElem = (Element) curNode;
          String tag = curElem.getTagName();
          if (tag == "Grammar") {
            Grammar g = XMLObjectCreator.processGrammarNode(curElem);
            grammars.addGrammar(g);
          } else if (tag == "Terminal") {
            Terminal t = XMLObjectCreator.processTerminalNode(curElem);
            grammars.addTerminal(t);
          } else if (tag == "Nonterminal") {
            Nonterminal nt = XMLObjectCreator.processNonterminalNode(curElem);
            grammars.addNonterminal(nt);
          } else if (tag == "Production") {
            Production p = XMLObjectCreator.processProductionNode(curElem);
            grammars.addProduction(p);
          }
          // skip precedence graph
          // skip disambiguation functions (not necessary since this uses
          // grammars that have removed all disambiguation functions to find
          // conflicts
            else if (tag == "ContextSets") {
              XMLObjectCreator.processContextSets(curElem, grammars);
          } else if (tag == "LALR_DFA") {
              dfa = XMLObjectCreator.processLALR_DFA(curElem);
          } else if (tag == "LRParseTable") {
              table = XMLObjectCreator.processLRParseTable(curElem);
          }
        }
        curNode = curNode.getNextSibling();
      }
      TreesitterConflicts ts_conflicts = new TreesitterConflicts(grammars);
      
      List<ParseCell> conflicts = table.getConflicts();
      for (ParseCell cell : conflicts) {
          DFA_State state = dfa.getState(cell.getParseStateNumber());
          // do cross referencing so you know if in progress shift or reducing
          state.setProductionForItems(grammars);
          ts_conflicts.addConflict(state.getPossibleParents(cell.getTerminalTag()));
      }
      System.out.println("Conflicts = \n" + ts_conflicts.toString());

      // add the conflicts to the new treesitter grammar
      File grammarJs = new File("grammar.js");
      BufferedWriter writer = new BufferedWriter(new FileWriter(grammarJs));
      BufferedReader reader = new BufferedReader(new FileReader(new File("grammarWithoutConflicts.js")));
      String line = reader.readLine();
      while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
        if (line.contains("conflicts: $ =>")) {
          writer.write(ts_conflicts.toString());
          writer.write(",");
          writer.newLine();
        }
      }
      reader.close();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
