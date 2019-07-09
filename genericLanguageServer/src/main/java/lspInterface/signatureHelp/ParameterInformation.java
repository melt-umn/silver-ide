package ablecLanguageServer;

/**
 * Represents a parameter of a callable-signature. A parameter can
 * have a label and a doc-comment.
 */
public class ParameterInformation implements LSPObject {
	/**
	 * The label of this parameter information.
	 *
	 * Either a string or an inclusive start and exclusive end offsets within its containing
	 * signature label. (see SignatureInformation.label). The offsets are based on a UTF-16
	 * string representation as `Position` and `Range` does.
	 *
	 * *Note*: a label of type string should be a substring of its containing signature label.
	 * Its intended use case is to highlight the parameter label part in the `SignatureInformation.label`.
	 */
	private String label; //LSP type: string | [number, number];

	/**
	 * The human-readable doc-comment of this parameter. Will be shown
	 * in the UI but can be omitted.
	 */
	private String documentation; // LSP type: string | MarkupContent;

  public ParameterInformation(String label) {
    this.label = label;
  }

  public ParameterInformation(String label, String docs) {
    this.label = label;
    this.documentation = docs;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("label", label);
    json.addKeyValuePair("documentation", documentation);
    json.endObject();
    return json.getJson();
  }
}
