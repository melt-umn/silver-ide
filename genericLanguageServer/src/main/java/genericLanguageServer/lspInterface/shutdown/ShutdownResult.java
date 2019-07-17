package genericLanguageServer.lspInterface.shutdown;

import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.JSONType;



public class ShutdownResult implements LSPResponse {
  public ShutdownResult() {

  }

  public String getJson() {
    return "null";
  }

  public Integer getJsonType() {
    return JSONType.NULL_TYPE;
  }
}
