package ablecLanguageServer;
public class Diagnostic implements LSPObject {
	/**
	 * The range at which the message applies.
	 */
  private Range range;
	/**
	 * The diagnostic's severity. Can be omitted. If omitted it is up to the
	 * client to interpret diagnostics as error, warning, info or hint.
	 */
  private DiagnosticSeverity severity;
	/**
	 * The diagnostic's code, which might appear in the user interface.
	 */
  private Integer code; // specification also allows this to be a code

	/**
	 * A human-readable string describing the source of this
	 * diagnostic, e.g. 'typescript' or 'super lint'.
	 */
	private String source;

	/**
	 * The diagnostic's message.
	 */
	private String message;

	/**
	 * An array of related diagnostic information, e.g. when symbol-names within
	 * a scope collide all definitions can be marked via this property.
	 */
	DiagnosticRelatedInformation[] relatedInformation;

  // only required info
  public Diagnostic(Range range, String message) {
    this.range = range;
    this.message = message;
  }

  public Diagnostic(
    Range range, DiagnosticSeverity severity, Integer code, String source, 
    String message, DiagnosticRelatedInformation[] relatedInfo) {
    this.range = range;
    this.severity = severity;
    this.code = code;
    this.source = source;
    this.message = message;
    this.relatedInformation = relatedInfo;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("range", range);
    json.addKeyValuePair("severity", severity);
    json.addKeyValuePair("code", code);
    json.addKeyValuePair("source", source);
    json.addKeyValuePair("message", message);
    json.addKeyValuePair("relatedInformation", relatedInformation);
    json.endObject();
    return json.getJson();
  }
}
