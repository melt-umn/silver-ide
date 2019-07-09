package ablecLanguageServer;
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

