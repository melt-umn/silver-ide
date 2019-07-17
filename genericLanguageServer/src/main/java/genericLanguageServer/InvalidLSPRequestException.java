package genericLanguageServer;

public class InvalidLSPRequestException extends Exception {
  private int errorCode;

  public InvalidLSPRequestException(String message) {
    super(message);
  }

  public InvalidLSPRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidLSPRequestException(Throwable cause) {
    super(cause);
  }

  public InvalidLSPRequestException(String message, int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
     return errorCode;
  }
}
