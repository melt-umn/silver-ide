package ablecLanguageServer;

import org.json.*;

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
