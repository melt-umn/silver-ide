package ablecLanguageServer;

/**
 * Signature help represents the signature of something
 * callable. There can be multiple signature but only one
 * active and only one active parameter.
 */
public class SignatureHelp implements LSPObject {
	/**
	 * One or more signatures.
	 */
	private SignatureInformation[] signatures;

	/**
	 * The active signature. If omitted or the value lies outside the
	 * range of `signatures` the value defaults to zero or is ignored if
	 * `signatures.length === 0`. Whenever possible implementors should
	 * make an active decision about the active signature and shouldn't
	 * rely on a default value.
	 * In future version of the protocol this property might become
	 * mandatory to better express this.
	 */
	private Integer activeSignature;

	/**
	 * The active parameter of the active signature. If omitted or the value
	 * lies outside the range of `signatures[activeSignature].parameters`
	 * defaults to 0 if the active signature has parameters. If
	 * the active signature has no parameters it is ignored.
	 * In future version of the protocol this property might become
	 * mandatory to better express the active parameter if the
	 * active signature does have any.
	 */
	private Integer activeParameter;

  public SignatureHelp(SignatureInformation[] signatures, Integer activeSig, Integer activeParam) {
    this.signatures = signatures;
    this.activeSignature = activeSig;
    this.activeParameter = activeParam;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("signatures", signatures);
    json.addKeyValuePair("activeSignature", activeSignature);
    json.addKeyValuePair("activeParameter", activeParameter);
    json.endObject();
    return json.getJson();
  }
}
