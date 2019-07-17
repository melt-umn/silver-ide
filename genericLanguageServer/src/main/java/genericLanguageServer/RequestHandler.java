package genericLanguageServer;

import genericLanguageServer.lspInterface.messages.*;
import genericLanguageServer.lspInterface.shutdown.*;

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

  public List<JSONObject> getNextMessage(BufferedReader r) throws IOException {
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

  public List<String> handleMessages(List<JSONObject> messages) throws InvalidLSPRequestException {
    List<String> responses = new ArrayList<String>(messages.size());
    for (JSONObject message : messages) {
      String possibleResponse = handleMessage(message);
      // was a request not a notification so add the response
      if (possibleResponse != null) {
        responses.add(possibleResponse);
      }
    }
    return responses;
  }

  public String handleMessage(JSONObject message) throws InvalidLSPRequestException {
    //if (!request.has("jsonrpc") || !request.get("jsonrpc").equals("2.0")) {
    //  throw new InvalidLSPRequestException("JSONRPC version 2.0 was not specified");
    //}
    //if (method.equals(ExitNotification.METHOD_NAME)) { // no params given
    //  params = null;
    //} else {
    return MethodDispatcher.dispatchMessage(message.toString());
  }
}
