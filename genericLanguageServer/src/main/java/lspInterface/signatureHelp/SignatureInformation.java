package ablecLanguageServer;
/**
 * Represents the signature of something callable. A signature
 * can have a label, like a function-name, a doc-comment, and
 * a set of parameters.
 */
public class SignatureInformation implements LSPObject {
	/**
	 * The label of this signature. Will be shown in
	 * the UI.
	 */
	private String label;

	/**
	 * The human-readable doc-comment of this signature. Will be shown
	 * in the UI but can be omitted.
	 */
	private String documentation; // MarkupContent also valid

	/**
	 * The parameters of this signature.
	 */
	private ParameterInformation[] parameters;

  public SignatureInformation(String label) {
    this.label = label;
  }

  public SignatureInformation(String label, String docs, ParameterInformation[] params) {
    this.label = label;
    this.documentation = docs;
    this.parameters = params;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("label", label);
    json.addKeyValuePair("documentation", documentation);
    json.addKeyValuePair("parameters", parameters);
    json.endObject();
    return json.getJson();
  }
}
