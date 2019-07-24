package genericLanguageServer;

import org.json.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws IOException, UnsupportedEncodingException, Exception {
    Logger logger = Logger.getLogger();
    logger.setupAndUseDefaultLogFiles();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    RequestHandler requestHandler = new RequestHandler();
    Server server = Server.getServer();

    try {
      while (true)
      {
        List<JSONObject> requests = new ArrayList<JSONObject>();
        requests = requestHandler.getNextMessage(reader);
        logger.logRequests(requests);

        List<String> responses = requestHandler.handleMessages(requests);
        for (String response : responses) {
          if (response != null) {
            logger.logResponse(response);
            System.out.print(response);
          }
        }
        server.sendAllServerMessages();
        logger.clearLargeLogFiles();
        logger.flush();
      }
    } catch (Exception e) {
      logger.logException(e);
      logger.logMessage("Exception caught and being thrown at top level");
      logger.close();
      throw e;
    }
    finally {
      logger.close();
      reader.close();
    }
  }
}