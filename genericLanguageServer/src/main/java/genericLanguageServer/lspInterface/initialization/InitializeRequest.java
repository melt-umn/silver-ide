package genericLanguageServer.lspInterface.initialization;

import genericLanguageServer.Server;
import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPResponse;

import org.json.*;

public class InitializeRequest implements LSPRequest {

  private InitializeParams params;

  public static final String METHOD_NAME = "initialize";

  public InitializeRequest(JSONObject params) {
    this.params = new InitializeParams(params);
  }

  public LSPResponse processRequest() {
    Server server = Server.getServer();
    server.setProcessId(params.getProcessId());
    server.setRootPath(params.getRootPath());
    server.setRootUri(params.getRootUri());
    server.setClientCapabilities(params.getClientCapabilities());
    server.setTrace(params.getTrace());
    server.setWorkspaceFolders(params.getWorkspaceFolders());
    LSPResponse response = new InitializeResult();
    return response;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
