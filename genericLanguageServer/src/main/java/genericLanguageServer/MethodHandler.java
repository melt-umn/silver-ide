package genericLanguageServer;

import org.json.*;
import genericLanguageServer.lspInterface.messages.ResponseMessage;
import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.LSPRequest;
import genericLanguageServer.lspInterface.LSPNotification;
import genericLanguageServer.lspInterface.messages.ResponseResult;
import genericLanguageServer.lspInterface.messages.RequestMessage;

public class MethodHandler {

  public static ResponseMessage handleRequest(RequestMessage message, LSPRequest request) {
    LSPResponse result = request.processRequest();
    ResponseResult response = new ResponseResult(result);
    ResponseMessage resMessage = new ResponseMessage(message, response);
    return resMessage;
  }

  public static void handleNotification(LSPNotification notif) {
    notif.processNotification();
  }

}
