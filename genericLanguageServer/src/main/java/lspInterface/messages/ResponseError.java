package ablecLanguageServer;

public class ResponseError {
  private int code;
  private String message;

  public ResponseError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getJson() {
    return "\"error\": {\"code\":"+ code + ",\"message\":\"" + message + "\"}";
  }
}
