package genericLanguageServer.lspInterface.folding;

import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.JSONType;
import genericLanguageServer.JSONBuilder;

public class FoldingRangeResult implements LSPResponse {
  FoldingRange[] result;

  public FoldingRangeResult(FoldingRange[] results) {
    this.result = results;
  }

  public String getJson() {
    // return value can be null
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
