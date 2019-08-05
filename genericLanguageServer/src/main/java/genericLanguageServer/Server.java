package genericLanguageServer;

import genericLanguageServer.silverInterface.*;

import java.util.List;
import java.util.ArrayList;

public class Server {
  // singleton instance of the server
  private static Server server;

  private static final boolean amLogging = true;
  private static Logger logger = Logger.getLogger();
  /**
  * The process Id of the parent process that started
  * the server. Is null if the process has not been started by another process.
  * If the parent process is not alive then the server should exit (see exit notification) its process.
  */
  private SilverHandlerInterface silverInterface;
  private State state;

  private Boolean shutdown;

  private String[] languageFileExtensions;
  private String firstLineRegex;
  // this code should be generated
  private void runStartupCode() {
    this.languageFileExtensions = new String[] {"xc"};
    this.firstLineRegex = null;
  }

  private Server() {
    runStartupCode();
    shutdown = false;
    try {
      Initialization.initSilver();
    } catch (Exception e) {
      logger.logException(e);
    }
    silverInterface = new SilverHandlerInterface();
    state = new State();
  }

  public SilverHandlerInterface getSilverHandlerInterface() {
    return silverInterface;
  }

  public State getState() {
    return state;
  }

  public static Server getServer() {
    if (server == null) {
      server = new Server();
    }
    return server;
  }

  public void sendAllServerMessages() {
    List<String> messagesToSend = SilverInterface.getServerInitiatedMessagesToSend(state);
    for (String message : messagesToSend) {
      String response = ResponseHandler.constructServerInitiatedMessage(message);
      if (amLogging) {
        logger.logResponse(response);
      }
      System.out.print(response);
    }
  }

  public void exitIfNecessary() {
    Pair<Boolean, Integer> exitInfo = SilverInterface.needToExit(state);
    if (exitInfo.fst()) {
      System.exit(exitInfo.snd());
    }
  }
}
