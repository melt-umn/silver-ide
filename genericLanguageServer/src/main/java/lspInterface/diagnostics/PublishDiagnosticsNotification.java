package ablecLanguageServer;
public class PublishDiagnosticsNotification implements ServerInitiatedLSPNotification {
  private PublishDiagnosticsParams params;

  public static final String METHOD_NAME = "textDocument/publishDiagnostics";

  public PublishDiagnosticsNotification(PublishDiagnosticsParams params) {
    this.params = params;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("jsonrpc", "2.0");
    json.addKeyValuePair("method", METHOD_NAME);
    json.addKeyValuePair("params", params);
    json.endObject();
    return json.getJson();
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
