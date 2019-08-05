package genericLanguageServer;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws IOException, UnsupportedEncodingException, Exception {
    Logger logger = Logger.getLogger();
    logger.setupAndUseDefaultLogFiles();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    MessageHandler messageHandler = new MessageHandler();
    int mainLoops = 0;
    Server server = Server.getServer();

    try {
      while (true)
      {
        List<String> messages = new ArrayList<String>();
        messages = messageHandler.getNextMessage(reader);
        logger.logRequests(messages);

        List<String> responses = messageHandler.handleMessages(messages);
        for (String response : responses) {
          if (response != null) {
            logger.logResponse(response);
            System.out.print(response);
          }
        }
        server.sendAllServerMessages();
        mainLoops++;
        logger.logMessage("Asked Silver for server messages for time " + mainLoops);
        logger.clearLargeLogFiles();
        logger.flush();
        server.exitIfNecessary();
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
