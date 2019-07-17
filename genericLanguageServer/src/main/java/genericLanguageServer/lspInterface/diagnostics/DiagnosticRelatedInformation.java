package genericLanguageServer.lspInterface.diagnostics;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;
import genericLanguageServer.lspInterface.document.Location;


/**
 * Represents a related message and source code location for a diagnostic. This should be
 * used to point to code locations that cause or related to a diagnostics, e.g when duplicating
 * a symbol in a scope.
 */
public class DiagnosticRelatedInformation implements LSPObject {
	/**
	 * The location of this related diagnostic information.
	 */
	private Location location;

	/**
	 * The message of this related diagnostic information.
	 */
	private String message;

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("location", location);
    json.addKeyValuePair("message", message);
    json.endObject();
    return json.getJson();
  }
}
