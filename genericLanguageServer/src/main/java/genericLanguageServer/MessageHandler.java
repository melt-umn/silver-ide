package genericLanguageServer;

import genericLanguageServer.lspInterface.messages.*;
import genericLanguageServer.lspInterface.shutdown.*;
import genericLanguageServer.silverInterface.*;

import org.json.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MessageHandler {
  Logger logger;
  public static final boolean amLogging = true;

  public MessageHandler() {
    if (amLogging) {
      logger = Logger.getLogger();
    }
  }

  public List<String> getNextMessage(BufferedReader r) throws IOException {
    String contentLengthStr = r.readLine();
    if (contentLengthStr == null) {
      logger.logMessage("reached end of input stream");
      return new ArrayList<String>();
    }
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

    List<String> messages = new ArrayList<String>(1);
    messages.add(requestContent);
    return messages;
  }

  public List<String> handleMessages(List<String> messages) {
    List<String> responses = new ArrayList<String>(messages.size());
    for (String message : messages) {
      String possibleResponse = handleMessage(message);
      // was a request not a notification so add the response
      if (possibleResponse != null) {
        responses.add(possibleResponse);
      }
    }
    return responses;
  }

  public String handleMessage(String message) {
    Server server = Server.getServer();
    String json = SilverInterface.handleMessage(
      server.getState(), message, server.getSilverHandlerInterface());
    // was notification so no response
    if (json == null) {
      return null;
    } 
    // was request so construct response
    else {
      return ResponseHandler.constructResponse(json);
    }
  }
}
