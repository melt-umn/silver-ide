package ablecLanguageServer;

public class ShutdownRequest implements LSPRequest {

  public static final String METHOD_NAME = "shutdown";

  public ShutdownRequest() {

  }

  public LSPResponse processRequest() {
    Server server = Server.getServer();
    server.shutdown();
    LSPResponse response = new ShutdownResult();
    return response;
  }

  public String getMethodName() {
    return METHOD_NAME;
  }
}
