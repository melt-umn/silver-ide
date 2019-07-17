package genericLanguageServer.lspInterface.generics;

import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONType;
import genericLanguageServer.JSONBuilder;


abstract public class NullableArrayLSPResponse<T extends LSPObject> implements LSPResponse {
  private T[] result;
  private boolean nullResponse;

  public NullableArrayLSPResponse(T[] result) {
    this.result = result;
    nullResponse = false;
  }

  public NullableArrayLSPResponse() {
    result = null;
    nullResponse = true;
  }

  public String getJson() {
    if (result == null) {
      return "null";
    }
    JSONBuilder json = new JSONBuilder();
    json.addArrayValue(result);
    return json.getJson();
  }

  public Integer getJsonType() {
    if (result == null) {
      return JSONType.NULL_TYPE;
    }
    return JSONType.ARRAY_TYPE;
  }
}
