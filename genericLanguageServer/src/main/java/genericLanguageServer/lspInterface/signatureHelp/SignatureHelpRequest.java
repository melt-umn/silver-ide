package genericLanguageServer.lspInterface.signatureHelp;

import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.document.TextDocumentPositionParams;
import genericLanguageServer.lspInterface.document.DocumentUri;
import genericLanguageServer.lspInterface.document.Position;

import genericLanguageServer.Server;


import org.json.*;

public class SignatureHelpRequest implements LSPRequest {
  private TextDocumentPositionParams params;

  public static final String METHOD_NAME = "textDocument/signatureHelp";

  public SignatureHelpRequest(JSONObject params) {
    this.params = new TextDocumentPositionParams(params);
  }

  public LSPResponse processRequest() {
    DocumentUri doc = params.getDocument();
    Position pos = params.getPosition();
    Server server = Server.getServer();
    SignatureHelp help = server.getSignatureHelp(doc, pos);
    SignatureHelpResult result = new SignatureHelpResult(help);
    return result;
  }


  public String getMethodName() {
    return METHOD_NAME;
  }
}
