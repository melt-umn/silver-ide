package ablecLanguageServer;

public interface LSPRequest {

   LSPResponse processRequest();
   String getMethodName();
}
