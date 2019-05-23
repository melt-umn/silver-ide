import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TreesitterConflicts {

  private List<List<Integer>> conflicts = new ArrayList<List<Integer>>();
  private Grammars grammars;

  public TreesitterConflicts(Grammars g) {
    grammars = g;
  }

  public void addConflict(List<Integer> conflict) {
    conflict = removeDuplicatesInConflict(conflict);
    if (!isDuplicateConflict(conflict)) {
      conflicts.add(conflict);
    }
  }

  public List<Integer> removeDuplicatesInConflict(List<Integer> oldConflicts) {
    List<Integer> duplicateFreeList = new ArrayList<Integer>();
    for (Integer i : oldConflicts) {
      if (!duplicateFreeList.contains(i)) {
        duplicateFreeList.add(i);
      }
    }
    return duplicateFreeList;
  }

  private String toTreesitterName(String silverName) {
    return "$." + silverName.replace(':', '_');
  }

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

