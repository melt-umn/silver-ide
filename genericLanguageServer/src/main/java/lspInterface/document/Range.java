package ablecLanguageServer;

import org.json.*;

public class Range implements LSPObject {
  private Position start;
  private Position end;

  public Range(Position start, Position end) {
	  /**
	  * The range's start position.
	  */
    this.start = start;
	  /**
	  * The range's end position.
	  */
    this.end = end;
  }

  public Range(Position start, int charsForward) {
    this.start = start;
    this.end = Position.getPositionForward(start, charsForward);
  }

  public Range(Position start, int linesForward, int charsForward) {
    this.start = start;
    this.end = Position.getPositionForward(start, linesForward, charsForward);
  }

  public Range(JSONObject obj) {
    start = new Position(obj.getJSONObject("start"));
    end = new Position(obj.getJSONObject("end"));
  }

  public static Range parseJson(JSONObject obj) {
    Position s = Position.parseJson(obj.getJSONObject("start"));
    Position e = Position.parseJson(obj.getJSONObject("end"));
    return new Range(s, e);
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("start", start);
    json.addKeyValuePair("end", end);
    json.endObject();
    return json.getJson();
  }

  public Position getStart() {
    return start;
  }

  public Position getEnd() {
    return end;
  }
}
