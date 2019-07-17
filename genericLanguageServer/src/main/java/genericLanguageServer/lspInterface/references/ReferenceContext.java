package genericLanguageServer.lspInterface.references;

import org.json.*;

public class ReferenceContext {
  private Boolean includeDeclaration;

  public ReferenceContext(JSONObject obj) {
    if (obj.has("includeDeclaration")) {
      includeDeclaration = obj.getBoolean("includeDeclaration");
    }
  }

  public Boolean shouldIncludeDeclaration() {
    return includeDeclaration;
  }
}
