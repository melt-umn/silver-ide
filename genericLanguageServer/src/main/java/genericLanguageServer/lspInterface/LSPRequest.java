package genericLanguageServer.lspInterface;

public interface LSPRequest {

   LSPResponse processRequest();
   String getMethodName();
}
