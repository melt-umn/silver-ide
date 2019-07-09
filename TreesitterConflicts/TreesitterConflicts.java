import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * TreesitterConflicts is a class that represents the conflicts needed to be specified in a Treesitter grammar.
 * @author      Joseph Blanchard <blanc317@umn.edu>
 * @version     1.0
 * @since       1.0
 */
public class TreesitterConflicts {
  /**
  * The list of conflicts containing a list of grammar symbols involved in the
  * conflict.
  */
  private List<List<Integer>> conflicts = new ArrayList<List<Integer>>();
  /**
  * The grammars this parse specification was generated from.
  */
  private Grammars grammars;

  /**
  * Constructs an empty collection of Treesitter conflicts.
  * @since 1.0
  */
  public TreesitterConflicts(Grammars g) {
    grammars = g;
  }

  /**
  * Adds a conflict to the conflicts and removes duplicate items in the conflict
  * and does not add it if it duplicates a conflict already in the conflicts.
  * @param conflict the conflict to add.
  * @since 1.0
  */
  public void addConflict(List<Integer> conflict) {
    conflict = removeDuplicatesInConflict(conflict);
    if (!isDuplicateConflict(conflict)) {
      conflicts.add(conflict);
    }
  }

  /**
  * Removes the grammar symbols that appear more than once in a conflict
  * @param oldConflicts the conflict before duplicate grammar symbols are removed.
  * @return a conflict with no duplicate grammar symbols.
  * @since 1.0
  */
  public List<Integer> removeDuplicatesInConflict(List<Integer> oldConflicts) {
    // remove all integers that appear more than once in the list
    List<Integer> duplicateFreeList = new ArrayList<Integer>();
    for (Integer i : oldConflicts) {
      if (!duplicateFreeList.contains(i)) {
        duplicateFreeList.add(i);
      }
    }
    return duplicateFreeList;
  }

  /**
  * Turns the silver name of grammar symbol into the equivalent treesitter identifier.
  * @return the equivalent treesitter identifer name for the given silver grammar symbol name.
  * @since 1.0
  */
  private String toTreesitterName(String silverName) {
    return "$." + silverName.replace(':', '_');
  }

  /**
  * Returns whether or not the specified conflict duplicates a conflict already in the conflicts collection.
  * @param newConflict the conflict to test against the conflicts already in the collcetion.
  * @return true if the specified conflict duplicates a conflict already in the conflicts and false otherwise.
  * @since 1.0
  */
  public boolean isDuplicateConflict(List<Integer> newConflict) {
    Collections.sort(newConflict);
    for (List<Integer> conflict : conflicts) {
      if (conflict.size() == newConflict.size()) {
        boolean identical = true;
        for (int j = 0; j < conflict.size() && identical; j++) {
          if (!conflict.get(j).equals(newConflict.get(j))) {
            identical = false;
          }
        }
        if (identical) {
          return identical;
        }
      }
    }
    return false;
  }

  /**
  * Returns a string representation of the Treesitter conflcits. This needs to fit the syntax
  * of the Treesitter grammars exactly as this is directly inserted into the grammar.js file.
  * @return a string representing the left hand side of the conflicts declaration in a Treesitter gramamr.
  * @since 1.0
  */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[\n");
    for (int i = 0; i < conflicts.size(); i++) {
      sb.append('[');
      for (int j = 0; j < conflicts.get(i).size(); j++) {
        sb.append(toTreesitterName(grammars.getDisplayNameByTag(conflicts.get(i).get(j))));
        if (j != conflicts.get(i).size()-1) {
          sb.append(", ");
        }
      }
      sb.append(']');
      if (i != conflicts.size()-1) {
        sb.append(",\n");
      }
    }
    sb.append("\n]");
    return sb.toString();
  }
}
