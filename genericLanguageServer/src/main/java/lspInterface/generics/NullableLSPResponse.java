package ablecLanguageServer;

public class NullableLSPResponse<T extends LSPObject> implements LSPResponse {
  private boolean nullResponse;
  private T result;
  
  public NullableLSPResponse() {
    nullResponse = true;
  }

  public NullableLSPResponse(T result) {
    this.result = result;
    nullResponse = false;
  }

  public boolean hasNullResponse() {
    return nullResponse;
  }

  public String getJson() {
    if (nullResponse) {
      return "null";
    }
    return result.getJson();
  }

  public Integer getJsonType() {
    if (nullResponse) {
      return JSONType.NULL_TYPE;
    }
    return JSONType.OBJECT_TYPE;
  }
}
