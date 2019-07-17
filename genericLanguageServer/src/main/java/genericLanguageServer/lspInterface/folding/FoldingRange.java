package genericLanguageServer.lspInterface.folding;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;
/**
package ablecLanguageServer;
 * Represents a folding range.
 */
public class FoldingRange implements LSPObject {

	/**
	 * The zero-based line number from where the folded range starts.
	 */
	Integer startLine;

	/**
	 * The zero-based character offset from where the folded range starts. If not defined, defaults to the length of the start line.
	 */
	Integer startCharacter;

	/**
	 * The zero-based line number where the folded range ends.
	 */
	Integer endLine;

	/**
	 * The zero-based character offset before the folded range ends. If not defined, defaults to the length of the end line.
	 */
	Integer endCharacter;

	/**
	 * Describes the kind of the folding range such as `comment' or 'region'. The kind
	 * is used to categorize folding ranges and used by commands like 'Fold all comments'.
	 */
	FoldingRangeKind kind;

  public FoldingRange(Integer startLine, Integer endLine) {
    this.startLine = startLine;
    this.endLine = endLine;
  }

  public FoldingRange(Integer startLine, Integer startChar, Integer endLine, Integer endChar, String kind) {
    this.startLine = startLine;
    this.startCharacter = startChar;
    this.endLine = endLine;
    this.endCharacter = endChar;
    this.kind = new FoldingRangeKind(kind);
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("startLine", startLine);
    json.addKeyValuePair("startCharacter", startCharacter);
    json.addKeyValuePair("endLine", endLine);
    json.addKeyValuePair("endCharacter", endCharacter);
    json.addKeyValuePair("kind", kind);
    json.endObject();
    return json.getJson();
  }
}
