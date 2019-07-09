package ablecLanguageServer;

import org.json.*;

public class CompletionRequest implements LSPRequest {
  private CompletionParams params;

  public static final String METHOD_NAME = "textDocument/completion";

  public CompletionRequest(JSONObject params) {
    this.params = new CompletionParams(params);
  }

  public LSPResponse processRequest() {
    DocumentUri doc = params.getDocument();
    Position p = params.getPosition();
    Server server = Server.getServer();
    CompletionItem[] items = server.getCompletionItems(doc, p);
    CompletionResult result = new CompletionResult(items);
    return result;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
