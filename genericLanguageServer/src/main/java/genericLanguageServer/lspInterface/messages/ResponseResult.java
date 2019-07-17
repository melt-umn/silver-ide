package genericLanguageServer.lspInterface.messages;

import org.json.*;
import genericLanguageServer.lspInterface.LSPResponse;
import genericLanguageServer.JSONType;

public class ResponseResult {
  private String stringResult;
  private Integer intResult;
  private JSONObject objResult;
  private JSONArray arrResult;
  private Boolean boolResult;
  private Integer resultType;

  // construct a result from initialize
  public ResponseResult(LSPResponse result) {
    resultType = result.getJsonType();
    if (resultType == JSONType.OBJECT_TYPE) {
      objResult = new JSONObject(result.getJson());
    } else if (resultType == JSONType.ARRAY_TYPE) {
      arrResult = new JSONArray(result.getJson());
    } else if (resultType == JSONType.NULL_TYPE) {
      // do nothing;
    }
  }

  public ResponseResult(String result) {
    stringResult = result;
    resultType = JSONType.STRING_TYPE;
  }
  public ResponseResult(Integer result) {
    intResult = result;
    resultType = JSONType.INT_TYPE;
  }
  public ResponseResult(JSONObject result) {
    objResult = result;
    resultType = JSONType.OBJECT_TYPE;
  }
  public ResponseResult(Boolean result) {
    boolResult = result;
    resultType = JSONType.BOOLEAN_TYPE;
  }
  public ResponseResult() {
    resultType = JSONType.NULL_TYPE;
  }

  public String getJson() {
    if (resultType == JSONType.NULL_TYPE) {
      return "\"result\":null";
    } else if (resultType == JSONType.INT_TYPE) {
      return "\"result\":" + intResult;
    } else if (resultType == JSONType.BOOLEAN_TYPE) {
      return "\"result\":" + boolResult.toString();
    } else if (resultType == JSONType.STRING_TYPE) {
      return "\"result\":\"" + stringResult + "\"";
    } else if (resultType == JSONType.OBJECT_TYPE) {
      return "\"result\":" + objResult.toString();
    } else if (resultType == JSONType.ARRAY_TYPE) {
      return "\"result\":" + arrResult.toString();
    } else {
      return ""; // TODO: get better error message
    }
  }
}
