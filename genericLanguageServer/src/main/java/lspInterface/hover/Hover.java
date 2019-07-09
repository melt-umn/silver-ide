package ablecLanguageServer;
/**
 * The result of a hover request.
 */
public class Hover implements LSPResponse, LSPObject {
	/**
	 * The hover's content
	 */
	String contents; //lspType : MarkedString | MarkedString[] | MarkupContent;

	/**
	 * An optional range is a range inside a text document
	 * that is used to visualize a hover, e.g. by changing the background color.
	 */
	Range range;

  public Hover(String contents) {
    this.contents = contents;
  }

  public Hover(String contents, Range range) {
    this.contents = contents;
    this.range = range;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("contents", contents);
    json.addKeyValuePair("range", range);
    json.endObject();
    return json.getJson();
  }

  public Integer getJsonType() {
    return JSONType.OBJECT_TYPE;
  }
}
