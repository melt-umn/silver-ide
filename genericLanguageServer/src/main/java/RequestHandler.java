package ablecLanguageServer;

import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class RequestHandler {
  Logger logger;
  public static final boolean amLogging = true;

  public RequestHandler() {
    if (amLogging) {
      logger = Logger.getLogger();
    }
  }

  public List<JSONObject> getNextRequest(BufferedReader r) throws IOException {
    logger.logMethodEntrance("getNextRequest");
    String contentLengthStr = r.readLine();
    String[] contentLengthVals = contentLengthStr.split(":");
    if (amLogging) {
      logger.logMessage(contentLengthStr);
    }
    int contentLength = Integer.parseInt(contentLengthVals[1].trim());
    char[] buffer = new char[contentLength];
    String throwawayLines;
    while ((throwawayLines = r.readLine()).trim().length() != 0) {
      logger.logMessage("Threw away: " + throwawayLines);
    } // consume rest of header
    r.read(buffer, 0, contentLength);
    String requestContent = new String(buffer);
    if (amLogging) {
      logger.logMessage("Request content: " + requestContent);
    }
    JSONTokener parser = new JSONTokener(requestContent);
    logger.logMethodExit("getNextRequest");
    return parseRequestJSON(parser);
  }
 
  private List<JSONObject> parseRequestJSON(JSONTokener parser) throws JSONException {
    logger.logMethodEntrance("parseRequestJSON");
    List<JSONObject> requests = new ArrayList<JSONObject>();
    Object request = parser.nextValue();
    if (request instanceof JSONArray) {
      JSONArray requestObjs = (JSONArray) request;
      int numRequests = requestObjs.length();
      for (int i = 0; i < numRequests; i++) { 
        requests.add(requestObjs.getJSONObject(i));
      }
    } else if (request instanceof JSONObject) {
      JSONObject requestObj = (JSONObject) request;
      requests.add(requestObj);
    } else if (request instanceof String) {
      String requestStr = (String) request;
      throw new JSONException("Parsed JSON was not an object or an array instead it was of type String with value: " + requestStr);
    } else {
      throw new JSONException("Parsed JSON was not an object or an array instead it was of type " + request.getClass());
    }
    logger.logMethodExit("parseRequestJSON");
    return requests;
  }

  public List<String> handleRequests(List<JSONObject> requests) throws InvalidLSPRequestException {
    logger.logMethodEntrance("handleRequests");
    List<String> responses = new ArrayList<String>(requests.size());
    for (JSONObject request : requests) {
      responses.add(handleRequest(request));
    }
    logger.logMethodExit("handleRequests");
    return responses;
  }

  public String handleRequest(JSONObject request) throws InvalidLSPRequestException {
    logger.logMethodEntrance("handleRequest");
    if (!request.has("jsonrpc") || !request.get("jsonrpc").equals("2.0")) {
      throw new InvalidLSPRequestException("JSONRPC version 2.0 was not specified");
    }
    if (!request.has("method")) {
      throw new InvalidLSPRequestException("No method was specified");
    }
    String method = request.getString("method");
    JSONObject params;
    if (method.equals(ExitNotification.METHOD_NAME)) { // no params given
      params = null;
    } else {
      if (!request.has("params")) {
        throw new InvalidLSPRequestException("No params specified");
      }
      Object genericParams = request.get("params");
      params = null;
      if (genericParams.equals(null)) {
        params = null;
      }
      else if (genericParams instanceof JSONObject) {
        params = (JSONObject) genericParams;
      } else {
        throw new InvalidLSPRequestException("Params are not a JSONObject instead they are: " + genericParams.getClass());
      }
    }
    if (MethodDispatcher.isRequestMethod(method)) {
      if (!request.has("id")) {
        throw new InvalidLSPRequestException("No id was specified for request method");
      }
      Object idObj = request.get("id");
      if (amLogging) {
        logger.logMessage("The id parameter has type " + idObj.getClass());
      }
      RequestMessage requestMsg;
      if (idObj instanceof String) {
        String id = (String) idObj;
        requestMsg = new RequestMessage(id, method, params);
      } else {
        Integer numId = (Integer) idObj;
        requestMsg = new RequestMessage(numId, method, params);
      }
      if (requestMsg == null && amLogging) {
        logger.logMessage("Request message is null before being sent to the MethodDispatcher");
      }
      ResponseMessage msg = MethodDispatcher.dispatchRequestMethod(method, requestMsg);
      logger.logMethodExit("handleRequest");
      return ResponseHandler.constructResponse(msg);

    } else { // is notification method
      NotificationMessage msg = new NotificationMessage(method, params);
      MethodDispatcher.dispatchNotificationMethod(method, msg);
      logger.logMethodExit("handleRequest");
      return null;
    }
  }
}
