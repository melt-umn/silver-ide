package genericLanguageServer.lspInterface.initialization;

import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;
import genericLanguageServer.JSONType;



public class InitializeResult implements LSPResponse, LSPObject {
  ServerCapabilities capabilities;

  // get default initialize result
  public InitializeResult() {
    capabilities = new ServerCapabilities();
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("capabilities", capabilities);
    json.endObject();
    return json.getJson();
  }

  public Integer getJsonType() {
    return JSONType.OBJECT_TYPE;
  }
}
