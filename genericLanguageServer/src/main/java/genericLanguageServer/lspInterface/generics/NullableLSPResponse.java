package genericLanguageServer.lspInterface.generics;

import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONType;
import genericLanguageServer.JSONBuilder;

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
