package genericLanguageServer.lspInterface;

public interface LSPNotification {

   void processNotification();
   String getMethodName();
}
