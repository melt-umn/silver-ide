package genericLanguageServer.lspInterface.document;

import genericLanguageServer.lspInterface.LSPObject;
import genericLanguageServer.JSONBuilder;

public class Location implements LSPObject {
  private DocumentUri uri;
  private Range range;

  public Location(DocumentUri uri, Range range) {
    this.uri = uri;
    this.range = range;
  }

  public String getJson() {
    JSONBuilder json = new JSONBuilder();
    json.startObject();
    json.addKeyValuePair("uri", uri);
    json.addKeyValuePair("range", range);
    json.endObject();
    return json.getJson();
  }
}
