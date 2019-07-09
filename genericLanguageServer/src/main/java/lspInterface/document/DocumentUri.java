package ablecLanguageServer;

public class DocumentUri implements LSPObject {
  private String uri;
  
  public DocumentUri(String uri) {
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }

  public String getJson() {
    return "\"" + uri + "\"";
  }

  public String toString() {
    return uri;
  }
}
