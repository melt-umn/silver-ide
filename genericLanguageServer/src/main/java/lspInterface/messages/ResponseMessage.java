package ablecLanguageServer;

public class ResponseMessage extends Message {
  private String stringId;
  private Integer numId;
  private Integer idType;
  
  private ResponseResult result;
  private ResponseError error;

  public boolean hasIntegerId() {
    return idType == RequestMessage.INTEGER_ID_TYPE;
  }

  public boolean isErrorResponse() {
    return error != null;
  }

  public ResponseMessage(RequestMessage request, ResponseResult result) {
    idType = request.getIdType();
    if (hasIntegerId()) {
      numId = request.getNumberId();
    } else {
      stringId = request.getStringId();
    }
    this.result = result;
  }

  public ResponseMessage(RequestMessage request, ResponseError error) {
    if (request.hasIntegerId()) {
      numId = request.getNumberId();
    } else {
      stringId = request.getStringId();
    }
    idType = request.getIdType();
    this.error = error;
  }

  public String getJson() {
    String idStr = hasIntegerId() ? numId.toString() : "\"" + stringId + "\"";
    String responseStr = isErrorResponse() ? error.getJson() : result.getJson();
    return "{\"jsonrpc\":\"2.0\",\"id\":" + idStr + "," + responseStr + "}";
  }
}
