package genericLanguageServer.lspInterface;

public interface ServerInitiatedLSPNotification {

   String getJson();
   String getMethodName();
}
