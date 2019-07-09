package ablecLanguageServer;

import org.json.*;

public class Position implements LSPObject {
	/**
	 * Line position in a document (zero-based).
	 */
  private Integer line;
	/**
	 * Character offset on a line in a document (zero-based). Assuming that the line is
	 * represented as a string, the `character` value represents the gap between the
	 * `character` and `character + 1`.
	 *
	 * If the character value is greater than the line length it defaults back to the
	 * line length.
	 */
  private Integer character;

  public Position(Integer line, Integer character) {
    this.line = line;
    this.character = character;
  }

  public Position(JSONObject obj) {
    line = obj.getInt("line");
    character = obj.getInt("character");
  }

  public static Position parseJson(JSONObject obj) {
    int lineNum = obj.getInt("line");
    int charNum = obj.getInt("character");
    return new Position(lineNum, charNum);
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("line", line);
    json.addKeyValuePair("character", character);
    json.endObject();
    return json.getJson();
  }
  
  public String toString() {
    return "line: " + line + ", character: " + character;
  }

  public Integer getLine() {
    return line;
  }

  public Integer getCharacter() {
    return character;
  }

  public static Position getPositionAtStartOfLine(Position p) {
    return new Position(p.getLine(), 0);
  }

  public static Position getPositionForward(Position p, int charsForward) {
    return new Position(p.getLine(), p.getCharacter() + charsForward);
  }

  public static Position getPositionForward(Position p, int linesForward, int charsForward) {
    return new Position(p.getLine() + linesForward, p.getCharacter() + charsForward);
  }

  public static Position getPositionLineAbove(Position p) {
    return new Position(p.getLine() - 1, p.getCharacter());
  }

  public static Position getPositionLineBelow(Position p) {
    return new Position(p.getLine() + 1, p.getCharacter());
  }

  public static Position getPositionAtStartOfFile() {
    return new Position(0, 0);
  }
}


